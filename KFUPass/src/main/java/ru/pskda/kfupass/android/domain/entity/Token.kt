package ru.pskda.kfupass.android.domain.entity

import ru.pskda.kfupass.android.data.room.entity.TokensDBEntity

data class Token(
    val token: String,
    val building: String,
    val address: String,
    val status: String
) {

    fun toTokensDBEntity(): TokensDBEntity = TokensDBEntity(
        id = 0,
        building = building,
        address = address,
        token = token,
        status = status
    )
}