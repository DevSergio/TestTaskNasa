package com.test.task.nasa.app.di

import com.google.gson.GsonBuilder
import com.test.task.nasa.app.data.remote.ApodWebService
import com.test.task.nasa.app.data.remote.NasaRemoteSource
import com.test.task.nasa.app.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.APOD_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideNasaService(retrofit: Retrofit): ApodWebService {
        return retrofit.create(ApodWebService::class.java)
    }

    @Provides
    @Singleton
    fun provideNasaClient(nasaService: ApodWebService): NasaRemoteSource {
        return NasaRemoteSource(nasaService)
    }
}