package com.test.task.nasa.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.task.nasa.app.data.model.Apod
import com.test.task.nasa.app.presentation.MainViewModel
import com.test.task.nasa.app.repository.NasaRepository
import com.test.task.nasa.app.utils.Resource
import org.junit.Rule
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals


class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val apodRepository = mockk<NasaRepository>()

    private val apod = Apod(
        copyright  = "today",
        date  = "test date",
        explanation = "test explanation",
        hdurl = "hd url test",
        media_type = "type test",
        title = "title test",
        url = "url test"
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `when viewmodel get data then it should call the repository`() = runBlockingTest {
        val viewModel = instantiateViewModel()

        val flow = flow {
            emit(Resource.Loading())
            delay(10)
            emit(Resource.Success(apod))
        }

        coEvery { apodRepository.getApod("2020-02-05","e2qn6rAldeIiJnWT8wep9") } returns
                flow
        val apod = Apod(
            copyright  = "today",
            date  = "test date",
            explanation = "test explanation",
            hdurl = "hd url test",
            media_type = "type test",
            title = "title test",
            url = "url test")

        viewModel.loadApod("2020-02-05")
        viewModel.apodData.observeForever {
            assertEquals(it, apod)
        }

    }

    private fun instantiateViewModel(): MainViewModel{
        return MainViewModel(apodRepository)
    }
}

