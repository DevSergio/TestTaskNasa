package com.test.task.nasa.app.repository

import com.test.task.nasa.app.data.model.Apod
import com.test.task.nasa.app.data.remote.NasaRemoteSource
import com.test.task.nasa.app.utils.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NasaRepository @Inject constructor(private val nasaRemoteSource: NasaRemoteSource) : INasaRepository {

    override suspend fun getApod(date: String, apiKey: String): Flow<Resource<Apod>> {
        return flow {
            nasaRemoteSource.getApod(date, apiKey).collect {
                when (it) {
                    is Resource.Success -> {
                        emit(it)
                    }
                    is Resource.Failure -> {
                        emit(it)
                    }
                    is Resource.Loading -> {
                    }
                }
            }
        }
    }
}



