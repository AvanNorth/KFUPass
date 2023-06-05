package ru.pskda.kfupass.android.data

import ru.pskda.kfupass.android.data.api.TokensApi
import ru.pskda.kfupass.android.data.mapper.TokenMapper
import ru.pskda.kfupass.android.data.model.TokenListRequest
import ru.pskda.kfupass.android.data.model.TokenListResponse
import ru.pskda.kfupass.android.data.room.dao.TokensDao
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.domain.entity.TokenList
import ru.pskda.kfupass.android.domain.repository.TokensRepository
import javax.inject.Inject

class TokensRepositoryImpl @Inject constructor(
    private val api: TokensApi,
    private val dao: TokensDao,
    private val mapper: TokenMapper
) : TokensRepository {
    override suspend fun checkTokens(tokens: TokenListRequest): TokenListResponse =
        api.checkTokens(tokens)

    override suspend fun saveToken(token: Token): Long =
        dao.insertToken(token.toTokensDBEntity())

    override suspend fun getTokens(): TokenList = mapper.toTokenList(dao.getTokenList())

    override suspend fun setToken(token: Token) {
        api.addToken(token)
    }
}