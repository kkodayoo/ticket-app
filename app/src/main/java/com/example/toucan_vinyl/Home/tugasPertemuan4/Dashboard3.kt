package com.example.toucan_vinyl.Home.tugasPertemuan4

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toucan_vinyl.Data.Api.ConcertApiClient
import com.example.toucan_vinyl.Home.concert.ConcertAdapter
import com.example.toucan_vinyl.Home.tugasPertemuan6.JpopFragment
import com.example.toucan_vinyl.Home.tugasPertemuan6.RecommendFragment
import com.example.toucan_vinyl.Home.tugasPertemuan6.UtaFragment
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.ActivityDashboard3Binding
import com.example.toucan_vinyl.databinding.ActivityDashboardBinding
import kotlinx.coroutines.launch

class Dashboard3 : AppCompatActivity() {

    private lateinit var binding: ActivityDashboard3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboard3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        replaceFragment(RecommendFragment())

        binding.cardGenre.setOnClickListener { replaceFragment(RecommendFragment()) }
        binding.cardGenre2.setOnClickListener { replaceFragment(JpopFragment()) }
        binding.cardGenre3.setOnClickListener { replaceFragment(UtaFragment()) }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Dashboard"
            subtitle = "Tombol Akses Halaman"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        val username = intent.getStringExtra("username")
        Log.e("Dashboard", "Username: $username")
        binding.nameText.text = "$username"

        // MEMANGGIL API
        loadConcertsFromApi()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadConcertsFromApi() {
        lifecycleScope.launch {
            try {
                // PANGGIL API
                val concerts = ConcertApiClient.apiService.getConcerts()
                val shuffledConcerts = concerts.shuffled()

                Log.d("API_RESULT", "Total item: ${concerts.size}")

                // SETUP RECYCLER VIEW
                val adapter = ConcertAdapter(concerts)
                val shuffledadapter = ConcertAdapter(shuffledConcerts)
//                binding.rvConcert.layoutManager = GridLayoutManager(this@Dashboard3,3)
                binding.rvConcert.layoutManager = LinearLayoutManager(this@Dashboard3, LinearLayoutManager.HORIZONTAL, false)
                binding.rvConcert.adapter = adapter
                binding.rvRecommended.layoutManager = LinearLayoutManager(this@Dashboard3, LinearLayoutManager.HORIZONTAL, false)
                binding.rvRecommended.adapter = shuffledadapter

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Error: ${e.message}")
                Toast.makeText(
                    this@Dashboard3,
                    "Gagal memuat data konser",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
