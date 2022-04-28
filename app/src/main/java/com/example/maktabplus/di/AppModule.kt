package com.example.maktabplus.di

import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.local.LocalDataSource
import com.example.maktabplus.data.local.db.MovieDao
import com.example.maktabplus.data.remote.RemoteDataSource
import com.example.maktabplus.data.remote.network.MovieApi
import com.example.maktabplus.di.annotation.LocalDataSourceAnnotation
import com.example.maktabplus.di.annotation.RemoteDataSourceAnnotation
import com.example.maktabplus.domain.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @RemoteDataSourceAnnotation // useless
    fun provideRemoteDataSource(
        api: MovieApi
    ): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    @Singleton
    @LocalDataSourceAnnotation // useless
    fun provideLocalDataSource(
        movieDao: MovieDao
    ): LocalDataSource {
        return LocalDataSource(movieDao)
    }

    @Provides
    @Singleton
    fun provideRepository(
        /* annotations are useless and they were just for practicing */
        @RemoteDataSourceAnnotation remote: RemoteDataSource,
        @LocalDataSourceAnnotation local: LocalDataSource
    ): MovieRepository {
        return MovieRepository(
            remote, local
        )
    }

    @Provides
    @Singleton
    fun provideUseCase(
        repository: MovieRepository
    ): MovieUseCase {
        return MovieUseCase(repository)
    }

}