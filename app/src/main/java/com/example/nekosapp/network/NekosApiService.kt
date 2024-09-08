package com.example.nekosapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.nekosapi.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NekosApiService {

    @GET("/v3/images/random")
    suspend fun getRandomImages(
        @Query("rating") rating: String = "safe",
        @Query("limit") limit: Int = 30
    ): RandomImages

    @GET("/v3/images/{id}")
    suspend fun getImageById(
        @Path("id") id: Int
    ): Item
}

object NekosApi {
    val retrofitService: NekosApiService by lazy {
        retrofit.create(NekosApiService::class.java)
    }
}