package com.example.toucan_vinyl.Wiki

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WikiTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah total tab
    override fun getItemCount(): Int = 4

    // Menentukan Fragment yang tampil berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Wiki1Fragment()       // Penjelasan singkat ONNX
            1 -> Wiki2Fragment()      // Menjelaskan format .onnx
            2 -> Wiki3Fragment()     // Konversi model ke ONNX
            3 -> Wiki4Fragment()  // Dataset project tiket
            else -> throw IllegalStateException("Posisi tab tidak valid: $position")
        }
    }
}
