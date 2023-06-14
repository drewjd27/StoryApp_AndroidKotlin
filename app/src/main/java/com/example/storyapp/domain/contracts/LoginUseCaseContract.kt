package com.example.storyapp.domain.contracts

import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface LoginUseCaseContract {
    operator fun invoke(email: String, password: String): Flow<ResultState<String>>
}