package com.example.storyapp.domain.contracts

import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface RegisterUseCaseContract {
    operator fun invoke(name: String, email: String, password: String): Flow<ResultState<String>>
}