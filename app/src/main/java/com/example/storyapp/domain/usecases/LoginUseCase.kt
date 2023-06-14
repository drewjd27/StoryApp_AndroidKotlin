package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.LoginUseCaseContract
import com.example.storyapp.domain.entities.UserEntity
import com.example.storyapp.domain.interfaces.AuthenticationRepository
import com.example.storyapp.domain.interfaces.UserPrefRepository
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val userPreferenceRepository: UserPrefRepository,
    private val authRepository: AuthenticationRepository,
) : LoginUseCaseContract {
    override operator fun invoke(email: String, password: String): Flow<ResultState<String>> =
        flow {
            emit(ResultState.Loading())
            authRepository.login(email, password).catch {
                emit(ResultState.Error(it.message.toString()))
            }.collect { result ->
                if (result.error) {
                    emit(ResultState.Error(result.message))
                } else {
                    result.loginResult.let {
                        userPreferenceRepository.saveUser(
                            UserEntity(
                                it.userId, it.name, it.token
                            )
                        )
                    }
                    emit(ResultState.Success(result.message))
                }
            }
        }
}