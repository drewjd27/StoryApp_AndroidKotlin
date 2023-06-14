package com.example.storyapp.ui.maps

import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.utils.ResultState

data class MapsViewState(
    val resultStories: ResultState<List<StoryEntity>> = ResultState.Idle(),
)