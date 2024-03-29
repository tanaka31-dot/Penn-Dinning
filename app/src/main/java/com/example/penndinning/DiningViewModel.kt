package com.example.penndinning

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiningViewModel : ViewModel() {

    private val _response = MutableLiveData<MutableList<Dining>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    val response: LiveData<MutableList<Dining>> get() = _response

    init {
        _response.value = mutableListOf()
        _isLoading.value = true
        getDinings()
    }

    private fun getDinings() {
        DiningApi.retrofitService.getDinings().enqueue(object :
            Callback<MutableList<Dining>> {
            //in the body of the two overridden methods
            override fun onResponse(
                call: Call<MutableList<Dining>>,
                response: Response<MutableList<Dining>>
            ) {
                _response.value = response.body()
                _isLoading.value = false
            }

            override fun onFailure(call: Call<MutableList<Dining>>, t: Throwable) {
                Log.i("API", "ERROR: " + t.message)
            }
        })
    }

    fun getDiningsList(): MutableList<Dining>? {
        return response.value
    }
}