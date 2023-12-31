package com.example.storyapp.data.response

data class StoryListResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryResponse>
)