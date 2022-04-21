package com.example.maktabplus.data.remote

import android.media.Image
import com.example.maktabplus.data.IDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ImageApi
): IDataSource {

    override suspend fun getImageList(): Response<List<com.example.maktabplus.data.model.Image>> {
        return api.getImageList(1)
    }

}