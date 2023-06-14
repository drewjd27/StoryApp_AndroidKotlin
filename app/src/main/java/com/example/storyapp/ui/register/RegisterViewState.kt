package com.example.storyapp.ui.register

import com.example.storyapp.utils.ResultState

data class RegisterViewState(
    val resultRegisterUser: ResultState<String> = ResultState.Idle()
)