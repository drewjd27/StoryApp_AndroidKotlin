package com.example.storyapp.data.response

data class LoginResponse(
    val loginResult: LoginResult,
    val error: Boolean,
    val message: String
)

data class LoginResult(
    val token: String,
    val userId: String,
    val name: String
)