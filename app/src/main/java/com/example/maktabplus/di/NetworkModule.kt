package com.example.maktabplus.di

import android.icu.util.TimeUnit
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.maktabplus.data.model.MovieDetailWithGenres
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.GenreResponse
import com.example.maktabplus.data.model.movie.MovieResponse
import com.example.maktabplus.data.remote.network.ImageApi
import com.example.maktabplus.data.remote.network.MovieApi
import com.example.maktabplus.utils.Mapper.jsonToGenreResponse
import com.example.maktabplus.utils.Mapper.jsonToMovieDetailWithGenres
import com.example.maktabplus.utils.Mapper.jsonToMovieResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request()
            val url = request.url().newBuilder()
                .addQueryParameter("api_key", "f5a5d7af59cba941f2d09648869ea4e3")
                .addQueryParameter("language", "en-US")
                .build()
            val new = request.newBuilder()
                .url(url)
                .build()
            it.proceed(new)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor,
        header: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(header)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(GenreResponse::class.java, JsonDeserializer { json, _, _ ->
                jsonToGenreResponse(json.asJsonObject)
            })
            .registerTypeAdapter(MovieResponse::class.java, JsonDeserializer { json, _, _ ->
                jsonToMovieResponse(json.asJsonObject)
            })
            .registerTypeAdapter(MovieDetailWithGenres::class.java, JsonDeserializer { json, _, _ ->
                jsonToMovieDetailWithGenres(json.asJsonObject)
            })
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageApi(
        retrofit: Retrofit
    ): ImageApi {
        return retrofit.create(ImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApi(
        retrofit: Retrofit
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}