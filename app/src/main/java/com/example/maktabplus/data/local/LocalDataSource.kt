package com.example.maktabplus.data.local

import android.media.Image
import com.example.maktabplus.data.IDataSource
import retrofit2.Response

class LocalDataSource : IDataSource {
    override suspend fun getImageList(): Response<List<Image>> {
        TODO("Not yet implemented")
    }

}