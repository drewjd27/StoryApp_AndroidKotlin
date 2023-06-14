package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.GetUserUseCaseContract
import com.example.storyapp.domain.entities.UserEntity
import com.example.storyapp.domain.interfaces.UserPrefRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(private val userPreferenceRepository: UserPrefRepository) :
    GetUserUseCaseContract {
    override fun invoke(): Flow<UserEntity> = userPreferenceRepository.userData
}