package com.test.task.nasa.app.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.task.nasa.app.data.model.Apod
import com.test.task.nasa.app.repository.INasaRepository
import com.test.task.nasa.app.utils.AppConstants.API_KEY
import com.test.task.nasa.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: INasaRepository) : ViewModel() {

    var apodData: MutableLiveData<Apod> = MutableLiveData()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var onMessageError = MutableLiveData<String>()

    fun loadApod(date: String) {
        viewModelScope.launch {
            repository.getApod(
                date = date,
                apiKey = API_KEY
            ).collect {
                when (it) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Failure -> {
                        isLoading.value = false
                        onMessageError.value = it.exception.message.toString()
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        apodData.value = it.data
                    }
                }
            }
        }
    }
}