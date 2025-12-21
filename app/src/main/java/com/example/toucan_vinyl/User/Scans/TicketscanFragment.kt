package com.example.toucan_vinyl.User.Scans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.FragmentTicketscanBinding
import com.google.android.material.tabs.TabLayoutMediator

class TicketscanFragment : Fragment() {

    private var _binding: FragmentTicketscanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketscanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter ViewPager
        val adapter = TicketscanTabsAdapter(requireActivity())
        binding.viewPagerWiki.adapter = adapter

        // TabLayoutMediator (2 Tab)
        TabLayoutMediator(binding.tabLayoutWiki, binding.viewPagerWiki) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Generate QR"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_qr)

                }
                1 -> {
                    tab.text = "Scan QR Ticket"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_scan)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
