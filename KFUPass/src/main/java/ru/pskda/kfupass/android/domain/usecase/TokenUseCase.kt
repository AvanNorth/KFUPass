package ru.pskda.kfupass.android.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.pskda.kfupass.android.data.mapper.TokenMapper
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.domain.entity.TokenList
import ru.pskda.kfupass.android.domain.repository.TokensRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(
    private val tokensRepository: TokensRepository,
    private val mapper: TokenMapper
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun saveToken(token: Token): Long =
        withContext(dispatcher) {
            tokensRepository.saveToken(token)
        }

    suspend fun getTokens(): TokenList =
        withContext(dispatcher) {
            tokensRepository.getTokens()
        }

    suspend fun checkTokens(tokens: TokenList): TokenList =
        withContext(dispatcher) {
            mapper.toTokenList(tokensRepository.checkTokens(mapper.toRequestList(tokens)))
        }
}