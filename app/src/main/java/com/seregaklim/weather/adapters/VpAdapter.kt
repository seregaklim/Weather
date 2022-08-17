package com.seregaklim.weather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(fa: FragmentActivity, private val list: List<Fragment>) : FragmentStateAdapter(fa) {
    // возвращает адаптеру количество элементов, которое у нас есть
    override fun getItemCount(): Int {
        return list.size
    }
    //возращает фрагмент, который хотим запустить
    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}