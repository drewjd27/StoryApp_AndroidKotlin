package com.example.storyapp.data.response

data class StoryDetailResponse(
    val error: Boolean,
    val message: String,
    val story: StoryResponse
)