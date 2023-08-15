package com.gc.mockresponseinterceptor.di

import android.content.Context
import com.gc.mockResponseInterceptor.HttpMockResponseInterceptor
import com.gc.mockresponseinterceptor.data.ApiConstants
import com.gc.mockresponseinterceptor.data.api.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    fun provideMockingInterceptor(
        @ApplicationContext context: Context
    ): HttpMockResponseInterceptor {
        return HttpMockResponseInterceptor.Builder(context.assets).isMockEnabled {
            false //enable it to true if you want data from local json file.
        }.build()
    }


    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        mockResponseInterceptor: HttpMockResponseInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(mockResponseInterceptor)

        return builder.build()

    }


    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): CharacterApi {
        return builder.build().create(CharacterApi::class.java)
    }


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())

    }


}