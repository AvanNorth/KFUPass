package ru.pskda.kfupass.android.data.model

import com.google.gson.annotations.SerializedName
import ru.pskda.kfupass.android.domain.entity.TokenList

data class TokenListRequest(
    @SerializedName("tokens")
    val tokens: TokenList
)