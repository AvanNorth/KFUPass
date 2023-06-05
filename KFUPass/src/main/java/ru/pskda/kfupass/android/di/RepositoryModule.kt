package ru.pskda.kfupass.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.pskda.kfupass.android.data.TokensRepositoryImpl
import ru.pskda.kfupass.android.domain.repository.TokensRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideTokensRepository(tokensRepositoryImpl: TokensRepositoryImpl): TokensRepository
}