package com.example.toucan_vinyl.Home.artist

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toucan_vinyl.Data.Api.ConcertApiClient
import com.example.toucan_vinyl.Data.Model.ConcertModel
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.ActivityArtistDetailBinding
import kotlinx.coroutines.launch

class ArtistsList : AppCompatActivity() {

    private lateinit var binding: ActivityArtistDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Daftar Artist"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        loadArtistsFromApi()
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

    private fun loadArtistsFromApi() {
        lifecycleScope.launch {
            try {
                val artists: List<ConcertModel> = ConcertApiClient.apiService.getConcerts()
                Log.d("API_RESULT", "Total artis: ${artists.size}")

                // Setup RecyclerView (pastikan rvConcert ada di layout)
                val adapter = ArtistsAdapter(artists)
                binding.rvConcert.layoutManager = LinearLayoutManager(this@ArtistsList)
                binding.rvConcert.adapter = adapter

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Error: ${e.message}")
                Toast.makeText(
                    this@ArtistsList,
                    "Gagal memuat data artis",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
