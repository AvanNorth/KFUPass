package ru.pskda.kfupass.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pskda.kfupass.android.App
import ru.pskda.kfupass.android.R
import ru.pskda.kfupass.android.data.api.TokensApi
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    private val app = App()

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.HEADERS
            )
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideTokenApi(
        gsonConverter: GsonConverterFactory,
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): TokensApi =
        Retrofit.Builder()
            .baseUrl("https://192.168.88.173")
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .addConverterFactory(gsonConverter)
            .build()
            .create(TokensApi::class.java)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LoggingInterceptor
}