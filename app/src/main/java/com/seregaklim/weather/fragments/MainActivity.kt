package com.seregaklim.weather.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seregaklim.weather.R
import com.seregaklim.weather.fragments.HoursFragment.Companion.newInstance

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // контейнер MainFragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()
    }
}