package com.example.maktabplus.di

import android.content.Context
import androidx.room.Room
import com.example.maktabplus.data.local.db.ImageDatabase
import com.example.maktabplus.data.local.db.MovieDao
import com.example.maktabplus.data.remote.network.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ImageDatabase {
        return Room.databaseBuilder(
            context,
            ImageDatabase::class.java,
            "image_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(
        database: ImageDatabase
    ): MovieDao {
        return database.getMovieDao()
    }

}