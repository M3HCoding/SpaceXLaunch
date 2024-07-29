package com.spacexlaunch.android.network

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    @Provides
    @Singleton
    fun provideRetrofit(): SpaceXInterface {
        return Retrofit.Builder().baseUrl("http://www.omdbapi.com/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SpaceXInterface::class.java)

    }

}

val client: OkHttpClient? = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .build()
        try {
            return@addInterceptor chain.proceed(newRequest)
        } catch (e: SocketTimeoutException) {
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "{}")
            return@addInterceptor Response.Builder()
                .code(500) // Set an appropriate HTTP status code
                .message("Socket timeout: ${e.message}")
                .request(originalRequest)
                .protocol(Protocol.HTTP_1_1)
                .body(responseBody)
                .build()
        } catch (e: UnknownHostException) {
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "{}")
            return@addInterceptor Response.Builder()
                .code(500) // Set an appropriate HTTP status code
                .message("Unknown host: ${e.message}")
                .request(originalRequest)
                .protocol(Protocol.HTTP_1_1)
                .body(responseBody)
                .build()
        } catch (e: Exception) {
            Log.e("Network Error", "SocketException: ${e.message}", e)
            throw e // Re-throwing will cause the request to fail
        }
    }
    .readTimeout(300, TimeUnit.SECONDS)
    .writeTimeout(300, TimeUnit.SECONDS)
    .connectTimeout(300, TimeUnit.SECONDS)
    .build()