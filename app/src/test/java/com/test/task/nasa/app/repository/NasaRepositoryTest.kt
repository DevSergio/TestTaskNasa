package com.test.task.nasa.app.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.task.nasa.app.data.model.Apod
import com.test.task.nasa.app.data.remote.NasaRemoteSource
import com.test.task.nasa.app.utils.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test

internal class NasaRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val remoteSource : NasaRemoteSource = mockk()
    private val apod : Apod = mockk()

    @Test
    fun shouldReturnApodBodyResponse_whenServiceWasCalledSuccessfully(){
        val apodRepository = instantiateRepository()

        val flow = flow {
            emit(Resource.Loading())
            delay(10)
            emit(Resource.Success(apod))
        }
        /*coEvery {
            apodRepository.getApod("2020-02-05", "e2qn6rAldeIiJnWT8wep9") } returns flow*/
    }

    private fun instantiateRepository(): NasaRepository {
        return NasaRepository(remoteSource)
    }
}