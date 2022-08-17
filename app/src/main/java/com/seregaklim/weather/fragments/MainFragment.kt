package com.seregaklim.weather.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.seregaklim.weather.adapters.VpAdapter
import com.seregaklim.weather.databinding.FragmentMainBinding
import com.seregaklim.weather.dto.WeatherModel
import com.seregaklim.weather.viewModel.MainViewModel
import org.json.JSONObject
import com.squareup.picasso.Picasso

//https://www.weatherapi.com/
const val API_KEY = " ab646ffb27fa4bc6807212908222707 "

class MainFragment : Fragment(){

    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tList = listOf(
        "Hours",
        "Days"
    )
    //разрешение пользователя на использование местоположения
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private lateinit var model: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrenCard()
        getWeather("Berlin")
    }
    private fun init() = with(binding){
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
                tab, pos -> tab.text = tList[pos]
        }.attach()

    }
    //получаем
    private fun updateCurrenCard()= with(binding){
        model.liveDataCurrent.observe(viewLifecycleOwner){

          val maxMinTemp = "${it.maxTemp}С°/${it.minTemp}С°"
            tvData.text=it.time
            tvCity.text=it.city
            tvCurrentTemp.text=it.currentTemp
            //погодные условия
            tvCondition.text=it.condition
            tvMaxMin.text = maxMinTemp
            Picasso.get().load("https"+it.imageUrl).into(imWeather)
        }
    }

    //разршение на предоставление местоположения
    private fun permissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            // информируем пользователя , выводим текст ,что дает его согласие
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }
    // если нет разрешения, запускаем permissionListener()
    private fun checkPermission(){
        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    //    http://api.weatherapi.com/v1/current.json?key=ab646ffb27fa4bc6807212908222707&q=Moscow&aqi=no
    //запрос погоды
    private fun getWeather(city: String) {

        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"

        // новая очередь запроса с библиотекой Volley
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    result -> parseWeatherData(result)

                //                val obj = JSONObject(result)
//                val temp = obj.getJSONObject("current")
//                Log.d("MyLog","Result: $result")
            },
            {
                    error->
                Log.d("MyLog","Volley error: $error")
            }
        )
        queue.add(stringRequest)

    }

    // получаем с сервера JSON и берем от туда нужные данные
    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)

        val list = parseDays(mainObject)
        parseCurrentData(mainObject,list[0])
    }

    //данные погоды с JSON на один день (сегодняшнего)
    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel){
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )

        //передаем
        model.liveDataCurrent.value=item


///логи jsona (тест)
        Log.d("MyLog", "City: ${item.maxTemp}")
        Log.d("MyLog", "Time: ${item.minTemp}")
        Log.d("MyLog", "Time: ${item.hours}")
    }


    //данные погоды с JSON по дням (парсим)
    private fun parseDays(mainObject: JSONObject): List<WeatherModel>{
        val list = ArrayList<WeatherModel>()

        val daysArray = mainObject.getJSONObject("forecast")
            //берем массив forecastday
            .getJSONArray("forecastday")

        val name =  mainObject.getJSONObject("location").getString("name")

        for (i in 0 until daysArray.length()){

            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),

                day.getJSONObject("day").getJSONObject("condition")
                    .getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),

                day.getJSONObject("day").getString("mintemp_c"),

                day.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),

                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        return list
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
