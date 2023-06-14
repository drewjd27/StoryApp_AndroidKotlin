package com.example.storyapp.domain.contracts

import androidx.paging.PagingData
import com.example.storyapp.domain.entities.StoryEntity
import kotlinx.coroutines.flow.Flow

interface GetStoriesUseCaseContract {
    operator fun invoke(): Flow<PagingData<StoryEntity>>
}