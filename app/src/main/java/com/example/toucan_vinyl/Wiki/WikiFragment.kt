package com.example.toucan_vinyl.Wiki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.FragmentWikiBinding
import com.google.android.material.tabs.TabLayoutMediator

class WikiFragment : Fragment() {

    private var _binding: FragmentWikiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWikiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Set Adapter ViewPager
        val adapter = WikiTabsAdapter(requireActivity())
        binding.viewPagerWiki.adapter = adapter

        // 2. TabLayoutMediator
        TabLayoutMediator(binding.tabLayoutWiki, binding.viewPagerWiki) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "ONNX Intro"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
                1 -> {
                    tab.text = "Format .ONNX"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
                2 -> {
                    tab.text = "Konversi Model"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
                3 -> {
                    tab.text = "Dataset Project"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
