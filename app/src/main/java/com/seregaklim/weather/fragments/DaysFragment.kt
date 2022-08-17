package com.seregaklim.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seregaklim.weather.R
import com.seregaklim.weather.viewModel.MainViewModel


class DaysFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}