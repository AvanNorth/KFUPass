package ru.pskda.kfupass.android.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.pskda.kfupass.android.data.room.dao.TokensDao
import ru.pskda.kfupass.android.data.room.entity.TokensDBEntity

@Database(
    version = 1,
    entities = [
        TokensDBEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTokensDao(): TokensDao
}