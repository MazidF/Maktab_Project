package com.example.maktabplus.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext

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

fun <T> Flow<T>.toResult(
    context: CoroutineContext = Dispatchers.IO,
    load: Boolean = false,
): Flow<Result<T>> = flow {
    onStart {
        if (load) {
            emit(Result.loading())
        }
    }.map {
        Result.success(it)
    }.catch { catch ->
        emit(Result.error(catch.message.toString()))
    }.flowOn(context)
}

fun Fragment.repeatingLaunch(state: Lifecycle.State, todo: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            todo()
        }
    }
}