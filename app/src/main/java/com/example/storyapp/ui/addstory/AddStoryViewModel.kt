package com.example.storyapp.ui.addstory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.domain.contracts.AddStoryUseCaseContract
import com.example.storyapp.domain.usecases.AddStoryUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.File

class AddStoryViewModel(private val addStoryUseCase: AddStoryUseCaseContract) : ViewModel() {
    private val _addStoryState = MutableStateFlow(AddStoryViewState())
    val addStoryState = _addStoryState.asStateFlow()

    fun addStory(file: File, description: String, latLng: LatLng?) {
        addStoryUseCase(file, description, latLng).onEach { result ->
            _addStoryState.update {
                it.copy(resultAddStory = result)
            }
        }.launchIn(viewModelScope)
    }

    class Factory(
        private val addStoryUseCase: AddStoryUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
                return AddStoryViewModel(addStoryUseCase) as T
            }
            error("Unknown ViewModel class: $modelClass")
        }
    }
}