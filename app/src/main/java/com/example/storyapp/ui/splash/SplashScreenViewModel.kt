package com.example.storyapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.domain.usecases.GetUserUseCase
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _splashScreenState = MutableStateFlow(SplashScreenViewState())
    val splashScreenState = _splashScreenState.asStateFlow()

    init {
        getIsLoggedIn()
    }

    private fun getIsLoggedIn() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                delay(3000)
                _splashScreenState.update {
                    it.copy(resultIsLoggedIn = ResultState.Success(user.token.isNotEmpty()))
                }
            }
        }
    }

    class Factory(
        private val getUserUseCase: GetUserUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
                return SplashScreenViewModel(getUserUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}