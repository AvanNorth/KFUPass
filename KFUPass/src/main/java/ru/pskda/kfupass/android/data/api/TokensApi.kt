package ru.pskda.kfupass.android.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.pskda.kfupass.android.data.model.TokenListRequest
import ru.pskda.kfupass.android.data.model.TokenListResponse
import ru.pskda.kfupass.android.domain.entity.Token

interface TokensApi {
    @GET("/checkTokens")
    fun checkTokens(@Body request: TokenListRequest): TokenListResponse

    @POST("/addToken")
    fun addToken(@Body request: Token)
}