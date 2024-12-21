package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.Constants
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.api.model.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val api = RetrofitInstance.weatherApi
    private val _result = MutableLiveData<NetworkResponse<WeatherModel>>()
     val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _result

    fun getData(city : String) {
        _result.value = NetworkResponse.Loading
        viewModelScope.launch {
         try {
             val response = api.getWeather(apiKey = Constants.apiKey, city = city)
             if (response.isSuccessful){
                 response.body()?.let { data ->
                     _result.value = NetworkResponse.Success(data)
                 }
             } else {
                 _result.value = NetworkResponse.Error("failed to load data")
             }

         } catch (e : Exception){
             _result.value = NetworkResponse.Error("failed to load data")
         }
        }
    }
}