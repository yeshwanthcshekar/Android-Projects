package com.example.amphibians.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Amphibian(
    val name: String,
    val type: String,
    val description: String,
    @SerialName(value = "img_src")
    val imageSource: String
)