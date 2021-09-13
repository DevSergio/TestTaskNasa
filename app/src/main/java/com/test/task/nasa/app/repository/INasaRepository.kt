package com.test.task.nasa.app.repository

import com.test.task.nasa.app.data.model.Apod
import com.test.task.nasa.app.utils.Resource
import kotlinx.coroutines.flow.Flow


interface INasaRepository {

    suspend fun getApod(date: String, apiKey : String ) : Flow<Resource<Apod>>
}