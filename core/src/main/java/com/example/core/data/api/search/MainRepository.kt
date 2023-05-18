package com.example.core.data.api.search

import com.example.core.data.api.search.datasource.LocalDataSource
import com.example.core.data.api.search.datasource.RemoteDataSource
import com.example.core.data.resource.Resource
import com.example.core.data.api.search.response.NationalizeData
import com.example.core.data.resource.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun search(query: String): Flow<Resource<NationalizeData>> {
        return flow {
            val result = remoteDataSource.search(query)
            if (result.status == Status.SUCCESS) {
                localDataSource.saveNationalizeData(result.data!!)
                emitAll(localDataSource.nationalizeData.map { Resource.success(it) })
            } else if (result.status == Status.ERROR) {
                emit(result)
            }
        }
    }

    suspend fun getCurrentResult(): Flow<Resource<NationalizeData>> {
        return flow {
            val currentData = localDataSource.observeCurrentNationalizeData()
            if (currentData.value == null || currentData.value?.country == null || currentData.value!!.country.isEmpty()) {
                emitAll(currentData.map { Resource.error(msg = "No data for this name, try again") })
            } else {
                emitAll(currentData.map { Resource.success(it) })
            }
        }
    }

}
