package com.example.toucan_vinyl.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.toucan_vinyl.AuthActivity
import com.example.toucan_vinyl.Home.tugasPertemuan2.RumusBangunan
import com.example.toucan_vinyl.Home.tugasPertemuan4.Dashboard
import com.example.toucan_vinyl.Home.tugasPertemuan4.DetailArtist
import com.example.toucan_vinyl.Home.tugasPertemuan5.WebViewActivity
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for requireContext() fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Main Activity"
            subtitle = "Tombol Akses Halaman"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
        binding.webviewButton.setOnClickListener {
            val i = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(i)
        }
        val username = sharedPref.getString("username", "User")
        binding.logoutButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    dialog.dismiss()
                    Log.e("Info Dialog", "Anda memilih Ya!")
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    requireActivity().finish()
                    startActivity(intent)
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                    Log.e("Info Dialog", "Anda memilih Tidak!")
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_LONG)
                        .setAction("Tutup") {
                            Log.e("Info Snackbar", "Snackbar ditutup")
                        }
                        .show()
                    Log.e("Info Snackbar", "Snackbar dibuka")
                }
                .show()
        }
        binding.custom1Button.setOnClickListener {
            val intent = Intent(requireContext(), DetailArtist::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
        binding.custom2Button.setOnClickListener {
            val intent = Intent(requireContext(), Dashboard::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
        binding.bangunanButton.setOnClickListener {
            val intent = Intent(requireContext(), RumusBangunan::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
    }
}