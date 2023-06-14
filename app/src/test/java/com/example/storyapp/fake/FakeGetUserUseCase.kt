package com.example.storyapp.fake

import com.example.storyapp.FakeFlowDelegate
import com.example.storyapp.domain.contracts.GetUserUseCaseContract
import com.example.storyapp.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

class FakeGetUserUseCase : GetUserUseCaseContract {
    val fakeDelegate = FakeFlowDelegate<UserEntity>()
    override fun invoke(): Flow<UserEntity> = fakeDelegate.flow
}