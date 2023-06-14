package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.GetStoriesUseCaseContract
import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.domain.interfaces.StoryRepository
import com.example.storyapp.domain.mapper.map
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetStoriesUseCase(private val storyRepository: StoryRepository) : GetStoriesUseCaseContract {
    override fun invoke() = storyRepository.getStories().map { pagingData ->
        pagingData.map()
    }
}