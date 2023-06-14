package com.example.storyapp.domain.entities

data class StoryEntity(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val lat: Double,
    val lng: Double,
)