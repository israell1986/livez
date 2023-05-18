package com.example.livez.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.api.search.MainRepository
import com.example.core.data.api.search.response.NationalizeData
import com.example.core.data.resource.Status
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: com.example.core.data.api.search.MainRepository
) : ViewModel() {


    private val _uiModel = MutableLiveData<UiState>()
    val uiModel: LiveData<UiState>
        get() = _uiModel

    init {
        fetchCurrentResult()
    }


    private fun fetchCurrentResult() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentResult().collect {
                if (it.status == com.example.core.data.resource.Status.SUCCESS) {
                    it.data?.let {
                        emitUiModel(predictions = it.country)
                    }
                } else {
                    emitUiModel(errorMsg = it.message)
                }
            }
        }

    }

    private suspend fun emitUiModel(
        predictions: List<com.example.core.data.api.search.response.NationalizeData.Country>? = null,
        errorMsg: String? = null
    ) {
        withContext(Dispatchers.Main) {
            _uiModel.value =
                UiState(
                    predictions = predictions,
                    errorMsg = errorMsg
                )
        }
    }

    data class UiState(
        val predictions: List<com.example.core.data.api.search.response.NationalizeData.Country>?,
        val errorMsg: String? = null
    )


}
