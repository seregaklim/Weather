package com.seregaklim.weather.dto

data class WeatherModel(
    val city: String,
    val time: String,
    //погодные условия
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String
)