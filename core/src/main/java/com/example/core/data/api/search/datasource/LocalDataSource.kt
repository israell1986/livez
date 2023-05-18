package com.example.core.data.api.search.datasource

import com.example.core.data.api.search.response.NationalizeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {

    private val _nationalizeData = MutableStateFlow<NationalizeData?>(null)
    val nationalizeData = _nationalizeData.asStateFlow()

    fun saveNationalizeData(nationalizeData: NationalizeData) {
        _nationalizeData.value = nationalizeData
    }

    fun observeCurrentNationalizeData(): StateFlow<NationalizeData?> {
        return nationalizeData
    }

}