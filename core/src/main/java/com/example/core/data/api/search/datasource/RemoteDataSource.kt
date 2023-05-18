package com.example.core.data.api.search.datasource

import com.example.core.data.api.ApiHelper
import com.example.core.data.api.search.response.NationalizeData
import com.example.core.data.resource.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun search(query: String): Resource<NationalizeData> {
        val response = apiHelper.search(query)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Resource.success(data = body)
            }
        }
        val message = "Failed to search ${response.code()} ${response.message()}"
        return Resource.error(message)

    }
}