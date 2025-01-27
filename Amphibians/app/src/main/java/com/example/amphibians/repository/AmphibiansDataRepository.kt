package com.example.amphibians.repository

import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApiService

interface AmphibiansDataRepository {
    suspend fun getAmphibiansData(): List<Amphibian>
}

class NetworkAmphibiansDataRepository(private val amphibianApiService: AmphibianApiService): AmphibiansDataRepository{
    override suspend fun getAmphibiansData(): List<Amphibian> {
        return amphibianApiService.getAmphibians()
    }
}