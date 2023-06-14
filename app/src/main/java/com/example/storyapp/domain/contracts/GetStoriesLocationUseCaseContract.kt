package com.example.storyapp.domain.contracts

import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface GetStoriesLocationUseCaseContract {
    operator fun invoke(): Flow<ResultState<List<StoryEntity>>>
}