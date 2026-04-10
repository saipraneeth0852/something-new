package com.statushub.india.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApi {
    @GET("v1/search")
    suspend fun searchWallpapers(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): PexelsResponse
}

data class PexelsResponse(
    val page: Int,
    val per_page: Int,
    val photos: List<PexelsPhoto>
)

data class PexelsPhoto(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val src: PexelsPhotoSource,
    val alt: String
)

data class PexelsPhotoSource(
    val original: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
