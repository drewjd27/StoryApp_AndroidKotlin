package com.example.storyapp.domain.interfaces

import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.StatusResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun login(email: String, password: String): Flow<LoginResponse>
    fun register(email: String, password: String, name: String): Flow<StatusResponse>
}