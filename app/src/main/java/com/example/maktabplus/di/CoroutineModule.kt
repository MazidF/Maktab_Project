package com.example.maktabplus.di

import com.example.maktabplus.di.annotation.IODispatcherAnnotation
import com.example.maktabplus.di.annotation.MainDispatcherAnnotation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutineModule {

    @Provides
    @Singleton
    @IODispatcherAnnotation
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcherAnnotation
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

}