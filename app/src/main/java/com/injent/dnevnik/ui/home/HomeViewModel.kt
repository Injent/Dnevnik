package com.injent.dnevnik.ui.home

import androidx.lifecycle.ViewModel
import com.injent.dnevnik.domain.repository.Repository
import com.injent.dnevnik.domain.model.UserContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()
}

data class HomeState(
    val error: String? = "",
)