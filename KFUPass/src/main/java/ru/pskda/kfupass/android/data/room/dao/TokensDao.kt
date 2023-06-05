package ru.pskda.kfupass.android.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import ru.pskda.kfupass.android.data.room.entity.TokensDBEntity
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.domain.entity.TokenList

@Dao
interface TokensDao {
    @Insert
    fun insertToken(token: TokensDBEntity): Long

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM tokens")
    fun getTokenList(): List<Token>
}