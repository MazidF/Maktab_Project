package com.example.maktabplus.data.remote

import com.example.maktabplus.data.model.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("v2/list")
    suspend fun getImageList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 100
    ): Response<List<Image>>
}
