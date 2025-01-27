package com.example.amphibians.repository

import com.example.amphibians.network.AmphibianApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer{
    val amphibiansDataRepository: AmphibiansDataRepository
}


class DefaultAppContainer: AppContainer{

    private val baseURL = "https://android-kotlin-fun-mars-server.appspot.com"

    val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseURL)
        .build()

    val retrofitService : AmphibianApiService by lazy { retrofit.create(AmphibianApiService::class.java) }


    override val amphibiansDataRepository: AmphibiansDataRepository by lazy {
        NetworkAmphibiansDataRepository(retrofitService)
    }
}