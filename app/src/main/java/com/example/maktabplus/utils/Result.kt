package com.example.maktabplus.utils

sealed class Result<T>(
    val data: T?,
    val error: String
) {
    class Success<T>(data: T?) : Result<T>(data, "")
    class Loading<T>(data: T? = null) : Result<T>(data, "")
    class Error<T>(error: String) : Result<T>(null, error)

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