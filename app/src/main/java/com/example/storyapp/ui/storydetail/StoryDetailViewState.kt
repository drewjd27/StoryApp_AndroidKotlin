package com.example.storyapp.ui.storydetail

import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.utils.ResultState

data class StoryDetailViewState(
    val resultStory: ResultState<StoryEntity> = ResultState.Idle()
)