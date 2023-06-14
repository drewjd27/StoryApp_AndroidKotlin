package com.example.storyapp.domain.contracts

import com.example.storyapp.utils.ResultState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AddStoryUseCaseContract {
    operator fun invoke(file: File, description: String, latLng: LatLng?): Flow<ResultState<String>>
}