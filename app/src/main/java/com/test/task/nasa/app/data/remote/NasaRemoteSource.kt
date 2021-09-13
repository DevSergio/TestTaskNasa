package com.test.task.nasa.app.data.remote

import com.test.task.nasa.app.data.model.Apod
import com.test.task.nasa.app.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NasaRemoteSource @Inject constructor(private val webService: ApodWebService) {

    suspend fun getApod(date: String, apiKey: String): Flow<Resource<Apod>> {
        return flow {
            try {
                emit(Resource.Success(webService.getApod(date, apiKey)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}