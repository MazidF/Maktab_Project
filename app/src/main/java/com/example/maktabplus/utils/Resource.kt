package com.example.maktabplus.utils

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Error<T>(val error: Throwable) : Resource<T>()

    val isSuccessful: Boolean = this is Success<T>

    companion object {
        fun <T> loading(data: T? = null) = Loading(data)
        fun <T> success(data: T?) = Success(data)
        fun error(error: Throwable) = Error<Unit>(error)
    }
}