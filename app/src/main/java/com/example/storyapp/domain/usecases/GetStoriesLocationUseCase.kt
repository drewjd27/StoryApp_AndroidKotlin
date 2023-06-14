package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.GetStoriesLocationUseCaseContract
import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.domain.interfaces.StoryRepository
import com.example.storyapp.domain.mapper.map
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetStoriesLocationUseCase(private val storyRepository: StoryRepository) :
    GetStoriesLocationUseCaseContract {

    override operator fun invoke(): Flow<ResultState<List<StoryEntity>>> = flow {
        emit(ResultState.Loading())
        storyRepository.getStoriesLocation(1).map {
            it.listStory.map()
        }.catch {
            emit(ResultState.Error(message = it.message.toString()))
        }.collect {
            emit(ResultState.Success(it))
        }
    }
}