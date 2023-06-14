package com.example.storyapp.ui.story

import androidx.paging.PagingData
import com.example.storyapp.domain.entities.StoryEntity
import com.example.storyapp.utils.ResultState

data class StoryViewState(
    val resultStories: PagingData<StoryEntity> = PagingData.empty(),
    val username: String = "",
)