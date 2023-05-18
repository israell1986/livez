package com.example.core.data.api

import com.example.core.data.api.search.response.NationalizeData
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: NationalizeService) : ApiHelper {

    override suspend fun search(query: String): Response<NationalizeData> {
        return apiService.search(query)
    }

}
