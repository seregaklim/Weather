package com.seregaklim.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seregaklim.weather.dto.WeatherModel

class MainViewModel : ViewModel(){
    val liveDataCurrent = MutableLiveData <WeatherModel>()
    val liveDataList = MutableLiveData<List <WeatherModel>>()
}