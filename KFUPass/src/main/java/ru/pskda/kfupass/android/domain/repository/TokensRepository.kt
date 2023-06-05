package ru.pskda.kfupass.android.domain.repository

import ru.pskda.kfupass.android.data.model.TokenListRequest
import ru.pskda.kfupass.android.data.model.TokenListResponse
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.domain.entity.TokenList

interface TokensRepository {
    suspend fun checkTokens(tokens: TokenListRequest): TokenListResponse
    suspend fun saveToken(token: Token): Long
    suspend fun getTokens(): TokenList
    suspend fun setToken(token: Token)
}