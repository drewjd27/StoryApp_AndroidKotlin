package com.example.storyapp.domain.contracts

import com.example.storyapp.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface GetUserUseCaseContract {
    operator fun invoke(): Flow<UserEntity>
}