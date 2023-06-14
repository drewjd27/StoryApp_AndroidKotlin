package com.example.storyapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.data.repository.AuthenticationRepositoryImplementation
import com.example.storyapp.data.repository.StoryRepositoryImplementation
import com.example.storyapp.data.source.database.StoryDatabase
import com.example.storyapp.data.source.local.UserPreferenceImplementation
import com.example.storyapp.data.source.remote.ApiConfig
import com.example.storyapp.domain.usecases.AddStoryUseCase
import com.example.storyapp.domain.usecases.GetStoriesLocationUseCase
import com.example.storyapp.domain.usecases.GetStoriesUseCase
import com.example.storyapp.domain.usecases.GetStoryDetailUseCase
import com.example.storyapp.domain.usecases.GetUserUseCase
import com.example.storyapp.domain.usecases.LoginUseCase
import com.example.storyapp.domain.usecases.LogoutUseCase
import com.example.storyapp.domain.usecases.RegisterUseCase
import com.example.storyapp.ui.addstory.AddStoryViewModel
import com.example.storyapp.ui.login.LoginViewModel
import com.example.storyapp.ui.maps.MapsViewModel
import com.example.storyapp.ui.register.RegisterViewModel
import com.example.storyapp.ui.splash.SplashScreenViewModel
import com.example.storyapp.ui.story.StoryViewModel
import com.example.storyapp.ui.storydetail.StoryDetailViewModel

object Locator {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing call: initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    val splashScreenViewModelFactory
        get() = SplashScreenViewModel.Factory(
            getUserUseCase = getUserUseCase
        )

    val loginViewModelFactory
        get() = LoginViewModel.Factory(
            loginUseCase = loginUseCase
        )
    val registerViewModelFactory
        get() = RegisterViewModel.Factory(
            registerUseCase = registerUseCase
        )

    val storyViewModelFactory
        get() = StoryViewModel.Factory(
            getStoriesUseCase = getStoriesUseCase,
            getUserUseCase = getUserUseCase,
            logoutUseCase = logoutUseCase
        )

    val mapsViewModelFactory
        get() = MapsViewModel.Factory(
            getStoriesLocationUseCase = getStoriesLocationUseCase
        )

    val storyDetailViewModelFactory
        get() = StoryDetailViewModel.Factory(
            getStoryDetailUseCase = getStoryDetailUseCase
        )

    val addStoryViewModelFactory
        get() = AddStoryViewModel.Factory(
            addStoryUseCase = addStoryUseCase
        )

    private val loginUseCase get() = LoginUseCase(userPreferencesRepository, authRepository)
    private val registerUseCase get() = RegisterUseCase(authRepository)
    private val getUserUseCase get() = GetUserUseCase(userPreferencesRepository)
    private val getStoriesUseCase get() = GetStoriesUseCase(storyRepository)
    private val logoutUseCase get() = LogoutUseCase(userPreferencesRepository)
    private val getStoryDetailUseCase get() = GetStoryDetailUseCase(storyRepository)
    private val addStoryUseCase get() = AddStoryUseCase(storyRepository)
    private val getStoriesLocationUseCase get() = GetStoriesLocationUseCase(storyRepository)

    private val userPreferencesRepository by lazy {
        UserPreferenceImplementation(requireApplication.dataStore)
    }
    private val authRepository by lazy {
        AuthenticationRepositoryImplementation(ApiConfig(requireApplication.dataStore).apiService)
    }
    private val storyRepository by lazy {
        StoryRepositoryImplementation(
            StoryDatabase.getDatabase(requireApplication),
            ApiConfig(requireApplication.dataStore).apiService
        )
    }
}