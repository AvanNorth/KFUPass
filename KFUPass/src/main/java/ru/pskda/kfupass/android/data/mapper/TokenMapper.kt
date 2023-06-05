package ru.pskda.kfupass.android.data.mapper

import ru.pskda.kfupass.android.data.model.TokenListRequest
import ru.pskda.kfupass.android.data.model.TokenListResponse
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.domain.entity.TokenList

class TokenMapper {
    fun toResponseList(list: TokenList): TokenListResponse = TokenListResponse(
        tokens = list
    )

    fun toRequestList(list: TokenList): TokenListRequest = TokenListRequest(
        tokens = list
    )

    fun toTokenList(list: TokenListResponse): TokenList = list.tokens

    fun toTokenList(list: List<Token>): TokenList = TokenList(list)

}