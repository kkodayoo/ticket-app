package com.example.toucan_vinyl.WelcomeScreen

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toucan_vinyl.AuthActivity
import com.example.toucan_vinyl.BaseActivity
import com.example.toucan_vinyl.databinding.FragmentWelcome1Binding
import com.example.toucan_vinyl.databinding.FragmentWelcome2Binding

class Welcome2Fragment : Fragment() {

    private var _binding: FragmentWelcome2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcome2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false) // gunakan key yang sama

        binding.btnGetStarted.setOnClickListener {
            val targetActivity = if (isLogin) {
                BaseActivity::class.java   // sudah login
            } else {
                AuthActivity::class.java   // belum login
            }

            val intent = Intent(requireContext(), targetActivity)
            startActivity(intent)
            requireActivity().finish() // agar tidak bisa kembali ke welcome screen
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
