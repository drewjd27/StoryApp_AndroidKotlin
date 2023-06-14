package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.GetStoryDetailUseCaseContract
import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.domain.interfaces.StoryRepository
import com.example.storyapp.domain.mapper.map
import com.example.storyapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetStoryDetailUseCase(private val storyRepository: StoryRepository) :
    GetStoryDetailUseCaseContract {
    override operator fun invoke(id: String): Flow<ResultState<StoryEntity>> = flow {
        emit(ResultState.Loading())
        storyRepository.getStoryDetail(id).map {
            it.story.map()
        }.catch {
            emit(ResultState.Error(message = it.message.toString()))
        }.collect {
            emit(ResultState.Success(it))
        }
    }
}