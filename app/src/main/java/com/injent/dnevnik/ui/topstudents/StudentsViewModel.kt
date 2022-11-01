package com.injent.dnevnik.ui.topstudents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injent.dnevnik.data.successOr
import com.injent.dnevnik.domain.model.ReportingPeriod
import com.injent.dnevnik.domain.model.Student
import com.injent.dnevnik.domain.repository.Repository
import com.injent.dnevnik.domain.use_cases.GetUser
import com.injent.dnevnik.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val repository: Repository,
    private val useCases: UseCases
): ViewModel() {

    private val _state = MutableStateFlow(StudentsState())
    val state: StateFlow<StudentsState>
        get() = _state.asStateFlow()

    init {
        refreshAll()
    }

    private fun refreshAll() {
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            val classmatesDeferred = async { repository.getClassmates() }
            val periodsDeferred = async { repository.getReportingPeriods() }

            val classmates = classmatesDeferred.await().successOr(emptyList())
            val periods = periodsDeferred.await().successOr(emptyList())
            val myId = useCases.getUser().userId

            for (student in classmates) {
                val mark = repository.getAverageMarks(student.id, periods[0].idStr)
                student.averageMark = mark.successOr(0f)
            }

            _state.update {
                it.copy(
                    loading = false,
                    periods = periods,
                    classmates = classmates.sortedWith(compareBy { item -> item.averageMark }).reversed(),
                    selectedPeriodId = periods[0].idStr,
                    myId = myId
                )
            }
        }
    }

    fun switchPeriod(periodId: String) {
        _state.update { it.copy(selectedPeriodId = periodId) }
        refreshAverageMarks()
    }

    fun refreshAverageMarks() {
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            val newList = _state.value.classmates
            for (student in newList) {
                val mark = repository.getAverageMarks(student.id, _state.value.selectedPeriodId)
                student.averageMark = mark.successOr(0f)
            }

            _state.update { it.copy(loading = false, classmates = newList.sortedWith(compareBy { item -> item.averageMark }).reversed()) }
        }
    }
}

data class StudentsState(
    val myId: Long = 0,
    val selectedPeriodId: String = "",
    val periods: List<ReportingPeriod> = emptyList(),
    val classmates: List<Student> = emptyList(),
    val loading: Boolean = true
)