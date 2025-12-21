package com.example.toucan_vinyl.User.Scans

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TicketscanTabsAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> QrcodeFragment()
            1 -> ScanFragment()
            else -> throw IllegalStateException("Posisi tab tidak valid: $position")
        }
    }
}
