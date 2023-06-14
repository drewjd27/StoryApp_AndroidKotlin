package com.example.storyapp.ui.login

import com.example.storyapp.utils.ResultState

data class LoginViewState(
    val resultVerifyUser: ResultState<String> = ResultState.Idle()
)