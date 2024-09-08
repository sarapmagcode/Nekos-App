package com.example.nekosapp.network

import com.squareup.moshi.Json

data class Tag(

    val description: String?,

    val id: Int?,

    @Json(name = "id_v2")
    val idV2: String?,

    @Json(name = "is_nsfw")
    val isNsfw: Boolean?,

    val name: String?,

    val sub: String?
)