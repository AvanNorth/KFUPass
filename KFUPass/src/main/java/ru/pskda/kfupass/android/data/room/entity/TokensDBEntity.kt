package ru.pskda.kfupass.android.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokensDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val building: String,
    val address: String,
    val status: String,
    val token: String
)
