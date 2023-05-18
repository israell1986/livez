package com.example.livez.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.api.search.MainRepository
import com.example.core.data.api.search.response.NationalizeData
import com.example.core.data.resource.Status
import com.example.livez.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: com.example.core.data.api.search.MainRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _uiModel = MutableLiveData<UiState>()
    val uiModel: LiveData<UiState>
        get() = _uiModel


    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            emitUiModel(isLoading = true)
            repository.search(query).collect {
                if (it.status == com.example.core.data.resource.Status.SUCCESS) {
                    it.data?.let {
                        emitUiModel(predictions = Event(it.country))
                    }
                } else {
                    emitUiModel(errorMsg = it.message)
                }
            }
        }

    }

    private suspend fun emitUiModel(
        isLoading: Boolean = false,
        predictions: Event<List<com.example.core.data.api.search.response.NationalizeData.Country>>? = null,
        errorMsg: String? = null
    ) {
        withContext(Dispatchers.Main) {
            _uiModel.value =
                UiState(
                    isLoading = isLoading,
                    predictions = predictions,
                    errorMsg = errorMsg
                )
        }
    }

    data class UiState(
        val isLoading: Boolean,
        val predictions: Event<List<com.example.core.data.api.search.response.NationalizeData.Country>>?,
        val errorMsg: String? = null
    )


}
