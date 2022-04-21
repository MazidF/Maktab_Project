package com.example.maktabplus.data.local

import com.example.maktabplus.data.IDataSource
import com.example.maktabplus.data.model.Image
import retrofit2.Response

class LocalDataSource : IDataSource {
    override suspend fun getImageList(): Response<List<Image>> {
        TODO("Not yet implemented")
    }

}