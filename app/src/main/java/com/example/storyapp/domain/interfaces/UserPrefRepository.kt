package com.example.storyapp.domain.interfaces

import com.example.storyapp.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserPrefRepository {
    val userData: Flow<UserEntity>
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun clearUser()
}