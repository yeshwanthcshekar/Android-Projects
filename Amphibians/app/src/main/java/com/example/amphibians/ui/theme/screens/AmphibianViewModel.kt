package com.example.amphibians.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibianApplication
import com.example.amphibians.network.Amphibian
import com.example.amphibians.repository.AmphibiansDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface AmphibianUiState{
    data class Success(val amphibianList: List<Amphibian>): AmphibianUiState
    object Loading : AmphibianUiState
    object Error : AmphibianUiState
}


class AmphibianViewModel(private val amphibiansDataRepository: AmphibiansDataRepository) : ViewModel() {

   private val _amphibianUiState = MutableStateFlow<AmphibianUiState>(AmphibianUiState.Loading)
    val amphibianUiState : StateFlow<AmphibianUiState> = _amphibianUiState.asStateFlow()

    init{
        getAmphibians()
    }

    private fun getAmphibians(){

        viewModelScope.launch {
            _amphibianUiState.update {
                AmphibianUiState.Success(amphibiansDataRepository.getAmphibiansData())
            }
            /*try {
                val amphibians = retrofitService.getAmphibians()
                _amphibianUiState.update { AmphibianUiState.Success(amphibians) }
            } catch (e: HttpException) {
                // Handle HTTP exceptions (4xx and 5xx errors)
                val errorCode = e.code()
                val errorMessage = e.message()
                _amphibianUiState.update { AmphibianUiState.Error }
                Log.e("AmphibianViewModel", "HTTP Error $errorCode: $errorMessage", e)
            } catch (e: IOException) {
                // Handle network exceptions (e.g., no internet connection)
                _amphibianUiState.update { AmphibianUiState.Error }
                Log.e("AmphibianViewModel", "Network error: ${e.message}", e)
            } catch (e: Exception) {
                // Handle other exceptions
                _amphibianUiState.update { AmphibianUiState.Error }
                Log.e("AmphibianViewModel", "Unknown error: ${e.message}", e)
            }*/
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibianApplication)
                val amphibiansDataRepository = application.container.amphibiansDataRepository
                AmphibianViewModel(amphibiansDataRepository = amphibiansDataRepository)
            }

        }
    }
}

