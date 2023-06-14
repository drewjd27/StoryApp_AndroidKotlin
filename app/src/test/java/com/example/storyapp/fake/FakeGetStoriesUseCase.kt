package com.example.storyapp.fake

import androidx.paging.PagingData
import com.example.storyapp.FakeFlowDelegate
import com.example.storyapp.domain.contracts.GetStoriesUseCaseContract
import com.example.storyapp.domain.entities.StoryEntity
import kotlinx.coroutines.flow.Flow

class FakeGetStoriesUseCase : GetStoriesUseCaseContract {
    val fakeDelegate = FakeFlowDelegate<PagingData<StoryEntity>>()
    override fun invoke(): Flow<PagingData<StoryEntity>> = fakeDelegate.flow
}