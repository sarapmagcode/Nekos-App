package com.example.nekosapp.network

import com.squareup.moshi.Json

data class Item(
    val artist: Any?,
    val characters: List<Any>?,

    @Json(name = "color_dominant")
    val colorDominant: List<Int>?,

    @Json(name = "color_palette")
    val colorPalette: List<List<Int>>?,

    @Json(name = "created_at")
    val createdAt: Double?,

    val duration: Any?,

    @Json(name = "hash_md5")
    val hashMd5: String?,

    @Json(name = "hash_perceptual")
    val hashPerceptual: String?,

    val id: Int?,

    @Json(name = "id_v2")
    val idV2: String?,

    @Json(name = "image_height")
    val imageHeight: Int?,

    @Json(name = "image_size")
    val imageSize: Int?,

    @Json(name = "image_url")
    val imageUrl: String?,

    @Json(name = "image_width")
    val imageWidth: Int?,

    @Json(name = "is_animated")
    val isAnimated: Boolean?,

    @Json(name = "is_flagged")
    val isFlagged: Boolean?,

    @Json(name = "is_original")
    val isOriginal: Boolean?,

    @Json(name = "is_screenshot")
    val isScreenshot: Boolean?,

    val rating: String?,

    @Json(name = "sample_height")
    val sampleHeight: Int?,

    @Json(name = "sample_size")
    val sampleSize: Int?,

    @Json(name = "sample_url")
    val sampleUrl: String?,

    @Json(name = "sample_width")
    val sampleWidth: Int?,

    val source: Any?,

    @Json(name = "source_id")
    val sourceId: Any?,

    val tags: List<Tag>?,

    @Json(name = "updated_at")
    val updatedAt: Double?,

    val verification: String?
)