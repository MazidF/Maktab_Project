package com.example.maktabplus.data

import com.example.maktabplus.data.model.image.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val remote: IDataSource,
    private val local: IDataSource
) {

    fun getImageList(): Flow<List<Image>> = flow {
        val response = remote.getImageList()
        if (response.isSuccessful) {
            emit(response.body() ?: listOf())
        }
    }
}