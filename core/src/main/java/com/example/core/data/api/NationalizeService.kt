package com.example.core.data.api

import com.example.core.data.api.search.response.NationalizeData
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NationalizeService {
    @GET("/")
    suspend fun search(
        @Query("name") name: String,
    ): Response<NationalizeData>
}
