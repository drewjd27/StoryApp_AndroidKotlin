package com.example.storyapp.fake

import com.example.storyapp.domain.contracts.LogoutUseCaseContract

class FakeLogoutUseCase : LogoutUseCaseContract {
    override suspend fun invoke() = Unit
}