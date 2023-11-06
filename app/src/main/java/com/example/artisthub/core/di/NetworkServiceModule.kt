package com.example.artisthub.core.di

import com.example.artisthub.BuildConfig
import com.example.artisthub.core.utils.network.NetworkCall
import com.example.artisthub.core.utils.network.NetworkCallImpl
import com.example.artisthub.features.artistdetail.data.api.ArtistAlbumAPI
import com.example.artisthub.features.searchartist.data.api.ArtistAPI
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkServiceModule = module {
    single { createWebService<ArtistAPI>(BuildConfig.MUSICBRAINZ_API_BASE_URL) }
    single { createWebService<ArtistAlbumAPI>(BuildConfig.MUSICBRAINZ_API_BASE_URL) }
    single<NetworkCall> { NetworkCallImpl() }
}

inline fun <reified T> createWebService(
     baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient())
        .build()
    return retrofit.create(T::class.java)
}

fun okHttpClient() = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor())
    .addInterceptor(headersInterceptor())
    .readTimeout(5, TimeUnit.MINUTES)
    .connectTimeout(  5, TimeUnit.MINUTES)
    .writeTimeout(5, TimeUnit.MINUTES)
    .build()

fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}

fun headersInterceptor() = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
        .addHeader("Accept", "application/json")
        .addHeader("User-Agent", "ArtistHub/1.0 ( ${BuildConfig.USER_AGENT_CONTACT_INFO} )")
        .build())
}



