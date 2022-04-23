package com.example.maktabplus.utils

sealed class Result<T> {
    class Success<T>(val data: T?) : Result<T>()
    class Loading<T>(val data: T? = null) : Result<T>()
    class Error<T>(val error: String) : Result<T>()

    val isSuccessful: Boolean = this is Success<T>

    companion object {

        fun <R, P> Result<R>.map(convert: (R?) -> P?): Result<P> {
            return when(this) {
                is Error -> error(this.error)
                is Loading -> loading(convert(this.data))
                is Success -> success(convert(this.data))
            }
        }

        fun <T> loading(data: T? = null) = Loading(data)
        fun <T> success(data: T?) = Success(data)
        fun <T> error(error: String) = Error<T>(error)
    }
}