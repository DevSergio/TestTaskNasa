package com.test.task.nasa.app.data.remote

import com.test.task.nasa.app.data.model.Apod
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaWebService {

    @GET("apod")
    suspend fun getApod(
        @Query(value = "date") date: String,
        @Query(value = "api_key") apiKey: String,
    ): Apod
}