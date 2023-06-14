package com.example.storyapp.domain.usecases

import com.example.storyapp.domain.contracts.AddStoryUseCaseContract
import com.example.storyapp.domain.interfaces.StoryRepository
import com.example.storyapp.utils.ResultState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.File

class AddStoryUseCase(private val storyRepository: StoryRepository) : AddStoryUseCaseContract {
    override operator fun invoke(
        file: File, description: String, latLng: LatLng?
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading())
        storyRepository.addStory(file, description, latLng).catch {
            emit(ResultState.Error(message = it.message.toString()))
        }.collect {
            emit(ResultState.Success(it.message))
        }
    }
}