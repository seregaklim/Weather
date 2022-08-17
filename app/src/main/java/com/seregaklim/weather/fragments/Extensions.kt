package com.seregaklim.weather.fragments

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

//проверка на уже ИМЕЮЩИЕСЯ разрешение на местоположения (можно расширить)
fun Fragment.isPermissionGranted(p: String): Boolean {
    return ContextCompat.checkSelfPermission(
        //сравнивает с константной GRANTED
        activity as AppCompatActivity, p) == PackageManager.PERMISSION_GRANTED
}