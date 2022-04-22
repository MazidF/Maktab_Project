package com.example.maktabplus.data.remote

import com.example.maktabplus.data.IDataSource
import com.example.maktabplus.data.model.image.Image
import com.example.maktabplus.data.remote.network.ImageApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ImageApi
): IDataSource {

    override suspend fun getImageList(): Response<List<Image>> {
        return api.getImageList(1)
    }

}