/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.NetworkMarsPhotosRepository
import com.example.marsphotos.network.MarsApiService
import com.example.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch
import java.io.IOException


/**
 * `MarsUiState` is a sealed interface representing the different states of a UI
 * that displays Mars photos. It uses a sealed interface to ensure type safety
 * and exhaustive `when` expressions.
 *
 * A sealed interface restricts the possible implementations to only those defined
 * within this same file, enabling the compiler to know all possible states at
 * compile time. This is crucial for exhaustive `when` statements and prevents
 * unexpected subclasses from being created elsewhere.
 */
sealed interface MarsUiState {

    /**
     * `Success` is a data class that represents the state when Mars photos have
     * been successfully retrieved. It implements the `MarsUiState` interface,
     * acting as a type marker.
     *
     * Even though `MarsUiState` has no methods to implement, `Success` still
     * "implements" it to signify that it is a valid state of the UI. This allows
     * treating `Success` objects as `MarsUiState` objects.
     */
    data class Success(val photos: List<MarsPhoto>) : MarsUiState

    /**
     * `Error` is a singleton object representing the state when an error occurred
     * while retrieving Mars photos. It implements the `MarsUiState` interface
     * for type marking.
     *
     * As a singleton object (`object`), only one instance of `Error` exists. This
     * is suitable because the error state doesn't require any additional data.
     */
    object Error : MarsUiState

    /**
     * `Loading` is a singleton object representing the state when the UI is
     * currently fetching Mars photos. It implements the `MarsUiState` interface
     * for type marking.
     *
     * Similar to `Error`, `Loading` is a singleton object as it doesn't need
     * any additional data.
     */
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = try{
                 MarsUiState.Success(marsPhotosRepository.getMarsPhotos())
            }catch (e: IOException){
                 MarsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}
