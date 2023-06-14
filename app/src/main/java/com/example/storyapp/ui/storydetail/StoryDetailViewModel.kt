package com.example.storyapp.ui.storydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyapp.domain.usecases.GetStoryDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class StoryDetailViewModel(private val getStoryDetailUseCase: GetStoryDetailUseCase) : ViewModel() {
        private val _storyDetailViewState = MutableStateFlow(StoryDetailViewState())
        val storyDetailViewState
        get() = _storyDetailViewState.asStateFlow()

        fun getStoryDetail(id: String) {
            getStoryDetailUseCase(id).onEach { result ->
                _storyDetailViewState.update {
                    it.copy(resultStory = result)
                }
            }.launchIn(viewModelScope)
        }

        class Factory(
            private val getStoryDetailUseCase: GetStoryDetailUseCase
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(StoryDetailViewModel::class.java)) {
                    return StoryDetailViewModel(getStoryDetailUseCase) as T
                }
                error("Unknown ViewModel class: $modelClass")
            }
        }
}