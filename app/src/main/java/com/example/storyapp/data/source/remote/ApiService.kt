package com.example.storyapp.data.source.remote

import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.StatusResponse
import com.example.storyapp.data.response.StoryDetailResponse
import com.example.storyapp.data.response.StoryListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body requestBody: HashMap<String, String>
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body requestBody: HashMap<String, String>
    ): StatusResponse

    @GET("stories")
    suspend fun stories(
        @Query("page") page: Int, @Query("size") size: Int
    ): StoryListResponse

    @GET("stories/{id}")
    suspend fun storyDetail(
        @Path("id") id: String
    ): StoryDetailResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?,
    ): StatusResponse

    @GET("stories")
    suspend fun storiesLocation(
        @Query("location") id: Int
    ): StoryListResponse
}