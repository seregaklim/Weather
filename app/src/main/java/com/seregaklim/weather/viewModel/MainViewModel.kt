package com.seregaklim.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seregaklim.weather.dto.WeatherModel


class MainViewModel : ViewModel(){
    // по часам
    val liveDataCurrent = MutableLiveData <WeatherModel>()
    //по дням
    val liveDataList = MutableLiveData<List <WeatherModel>>()
}