package com.example.livez

import com.example.core.data.api.ApiHelper
import com.example.core.data.api.ApiHelperImpl
import com.example.core.data.api.NationalizeService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAnalyticsService(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): com.example.core.data.api.NationalizeService {
        return retrofit.create(com.example.core.data.api.NationalizeService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.nationalize.io"
    }
}

@Module
@InstallIn(
    SingletonComponent::class
)
abstract class SearchModule {

    @Binds
    abstract fun bindAnalyticsService(
        predictionsServiceImpl: com.example.core.data.api.ApiHelperImpl
    ): com.example.core.data.api.ApiHelper
}