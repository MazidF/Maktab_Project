package com.example.maktabplus.di

import com.example.maktabplus.data.IDataSource
import com.example.maktabplus.data.ImageRepository
import com.example.maktabplus.data.local.LocalDataSource
import com.example.maktabplus.data.remote.ImageApi
import com.example.maktabplus.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteDataSourceAnnotation

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LocalDataSourceAnnotation

    @Provides
    @Singleton
    @RemoteDataSourceAnnotation
    fun provideRemoteDataSource(
        api: ImageApi
    ): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    @Singleton
    @LocalDataSourceAnnotation
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSource()
    }

    @Provides
    fun provideRepository(
        @RemoteDataSourceAnnotation remote: IDataSource,
        @LocalDataSourceAnnotation local: IDataSource
    ): ImageRepository {
        return ImageRepository(
            remote, local
        )
    }

}