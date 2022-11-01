package com.injent.dnevnik.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injent.dnevnik.data.Result
import com.injent.dnevnik.domain.model.Student
import com.injent.dnevnik.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _state = MutableStateFlow(ProfileUIState())

    val state: StateFlow<ProfileUIState>
        get() = _state.asStateFlow()

    fun loadUser(userId: Long) {
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            when (val profile = repository.getUserProfile(userId)) {
                is Result.Success -> _state.update { it.copy(user = profile.data, loading = false) }
                is Result.Error -> _state.update { it.copy(error = "Error", loading = false) }
                is Result.Loading -> { }
            }
        }
    }
}

data class ProfileUIState(
    val loading: Boolean = false,
    val user: Student? = null,
    val error: String? = null
)