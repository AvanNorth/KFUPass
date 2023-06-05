package ru.pskda.kfupass.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.pskda.kfupass.android.data.mapper.TokenMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideTokenMapper(): TokenMapper = TokenMapper()
}