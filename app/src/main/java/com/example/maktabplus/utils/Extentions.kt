package com.example.maktabplus.utils

import android.util.Log
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.lang.reflect.Type

fun logger(msg: String) {
    Log.d("tamrin17", msg)
}

fun <T> getCollectionClass(): Class<out Type> {
    return object : TypeToken<T>() {}.type::class.java
}

fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        Result.success(this.body())
    } else {
        Result.error(this.errorBody().toString())
    }
}

fun <T> Flow<T>.toResult(load: Boolean = false): Flow<Result<T>> = flow {
    onStart {
        if (load) {
            emit(Result.loading())
        }
    }.map {
        Result.success(it)
    }.catch { catch ->
        emit(Result.error(catch.message.toString()))
    }
}