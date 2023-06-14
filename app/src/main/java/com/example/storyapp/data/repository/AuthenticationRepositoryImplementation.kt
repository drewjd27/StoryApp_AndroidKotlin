package com.example.storyapp.data.repository

import com.example.storyapp.data.source.remote.ApiService
import com.example.storyapp.domain.interfaces.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthenticationRepositoryImplementation(private val api: ApiService) :
    AuthenticationRepository {
    override fun register(email: String, password: String, name: String) = flow {
        emit(
            api.register(
                hashMapOf(
                    Pair("name", name),
                    Pair("password", password),
                    Pair("email", email),
                )
            )
        )
    }.flowOn(Dispatchers.IO)

    override fun login(email: String, password: String) = flow {
        emit(
            api.login(
                hashMapOf(
                    Pair("password", password),
                    Pair("email", email),
                )
            )
        )
    }.flowOn(Dispatchers.IO)
}