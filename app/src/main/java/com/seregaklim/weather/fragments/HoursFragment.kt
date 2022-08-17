package com.seregaklim.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.seregaklim.weather.adapters.WeatherAdapter
import com.seregaklim.weather.databinding.FragmentHoursBinding
import com.seregaklim.weather.dto.WeatherModel

class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter
        val list = listOf(
            WeatherModel(
                "Moscow","12:00",
                "Sunny","25ºC",
                "", "", "",""),
            WeatherModel(
                "Moscow","13:00",
                "Sunny","25ºC",
                "", "", "",""),
            WeatherModel(
                "Moscow","14:00",
                "Sunny","35ºC",
                "", "", "","")
        )
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}