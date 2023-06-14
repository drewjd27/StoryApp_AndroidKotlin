package com.example.storyapp.data.source.remote


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.storyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig(private val dataStore: DataStore<Preferences>) {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(
            OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ).addInterceptor(AuthenticationInterceptor(dataStore)).build()
        ).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}