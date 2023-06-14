package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.LogoutUseCaseContract
import com.example.storyapp.domain.interfaces.UserPrefRepository

class LogoutUseCase(private val userPreferenceRepository: UserPrefRepository) :
    LogoutUseCaseContract {
    override suspend fun invoke() = userPreferenceRepository.clearUser()
}