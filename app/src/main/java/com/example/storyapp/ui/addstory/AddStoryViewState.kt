package com.example.storyapp.ui.addstory

import com.example.storyapp.utils.ResultState

data class AddStoryViewState(
    val resultAddStory: ResultState<String> = ResultState.Idle()
)