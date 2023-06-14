package com.example.storyapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.storyapp.data.source.database.StoryDatabase
import com.google.android.gms.maps.model.LatLng
import com.example.storyapp.data.paging.StoryRemoteMediator
import com.example.storyapp.data.response.StoryResponse
import com.example.storyapp.data.source.remote.ApiService
import com.example.storyapp.domain.interfaces.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryRepositoryImplementation(
    private val storyDatabase: StoryDatabase, private val api: ApiService
) : StoryRepository {
    override fun getStories(): Flow<PagingData<StoryResponse>> {
        @OptIn(ExperimentalPagingApi::class) return Pager(config = PagingConfig(
            pageSize = 5
        ), remoteMediator = StoryRemoteMediator(storyDatabase, api), pagingSourceFactory = {
            storyDatabase.storyDao().getAllStories()
        }).flow
    }

    override fun getStoryDetail(id: String) = flow {
        emit(
            api.storyDetail(id)
        )
    }.flowOn(Dispatchers.IO)

    override fun addStory(file: File, description: String, latLng: LatLng?) = flow {
        val requestBody = MultipartBody.Part.createFormData(
            "photo", file.name, file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        val desc = description.toRequestBody("text/plain".toMediaType())
        val lat = latLng?.latitude?.toFloat()
        val lng = latLng?.longitude?.toFloat()

        emit(
            api.addStory(requestBody, desc, lat, lng)
        )
    }.flowOn(Dispatchers.IO)

    override fun getStoriesLocation(id: Int) = flow {
        emit(
            api.storiesLocation(id)
        )
    }.flowOn(Dispatchers.IO)
}