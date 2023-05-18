package com.example.core.data.api

import com.example.core.data.api.search.response.NationalizeData
import retrofit2.Response

interface ApiHelper {

    suspend fun search(query: String): Response<NationalizeData>
}


