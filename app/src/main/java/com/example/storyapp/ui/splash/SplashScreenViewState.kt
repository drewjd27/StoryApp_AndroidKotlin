package com.example.storyapp.ui.splash

import com.example.storyapp.utils.ResultState

data class SplashScreenViewState(
    val resultIsLoggedIn: ResultState<Boolean> = ResultState.Idle()
)