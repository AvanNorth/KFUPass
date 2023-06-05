package ru.pskda.kfupass.android.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.pskda.kfupass.android.data.room.AppDatabase
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "KFUPassDB"
    ).setQueryCallback({ sqlQuery, bindArgs ->
        println("SQL Query: $sqlQuery SQL Args: $bindArgs")
    }, Executors.newSingleThreadExecutor()).build()

    @Provides
    @Singleton
    fun provideTokensDao(db: AppDatabase) = db.getTokensDao()
}