package com.example.maktabplus.data

import com.example.maktabplus.data.model.Image
import retrofit2.Response

interface IDataSource {

    suspend fun getImageList(): Response<List<Image>>
}