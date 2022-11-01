package com.injent.dnevnik.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injent.dnevnik.data.Result.*
import com.injent.dnevnik.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState>
        get() = _state.asStateFlow()

    fun auth(token: String, onResult: (success: Boolean) -> Unit) = viewModelScope.launch {
        when (val authed = repository.init(token)) {
            is Success -> onResult(authed.data)
            is Error -> onResult(false)
            is Loading -> { _state.update { it.copy(isLoading = true) } }
        }
    }
}

data class SignInState(
    val error: String = "",
    val isLoading: Boolean = false
)