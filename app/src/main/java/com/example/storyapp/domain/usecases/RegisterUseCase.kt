package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.RegisterUseCaseContract
import com.example.storyapp.domain.interfaces.AuthenticationRepository
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RegisterUseCase(
    private val authRepository: AuthenticationRepository,
) : RegisterUseCaseContract {
    override operator fun invoke(
        name: String, email: String, password: String
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading())
        authRepository.register(
            email, password, name
        ).catch {
            emit(ResultState.Error(it.message.toString()))
        }.collect { result ->
            if (result.error) {
                emit(ResultState.Error(result.message))
            } else {
                emit(ResultState.Success(result.message))
            }
        }
    }
}