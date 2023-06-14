package com.example.storyapp.domain.interfaces

import androidx.paging.PagingData
import com.example.storyapp.data.response.StatusResponse
import com.example.storyapp.data.response.StoryDetailResponse
import com.example.storyapp.data.response.StoryListResponse
import com.example.storyapp.data.response.StoryResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoryRepository {
    fun getStories(): Flow<PagingData<StoryResponse>>
    fun getStoryDetail(id: String): Flow<StoryDetailResponse>
    fun addStory(file: File, description: String, latLng: LatLng?): Flow<StatusResponse>
    fun getStoriesLocation(id: Int): Flow<StoryListResponse>
}