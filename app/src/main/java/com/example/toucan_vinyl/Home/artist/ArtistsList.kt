package com.example.toucan_vinyl.Home.artist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    /**
     * Launcher permission notifikasi (Android 13+)
     */
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    this,
                    "Izin notifikasi diberikan",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Notifikasi ditolak, reminder mungkin tidak tampil",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
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

        // 🔔 Cek & minta izin notifikasi
        checkNotificationPermission()

        // Load data artis
        loadArtistsFromApi()
    }

    /**
     * Cek permission POST_NOTIFICATIONS (WAJIB Android 13+)
     */
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(permission)
            }
        }
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

    /**
     * Ambil data artis dari API dan tampilkan ke RecyclerView
     */
    private fun loadArtistsFromApi() {
        lifecycleScope.launch {
            try {
                val artists: List<ConcertModel> =
                    ConcertApiClient.apiService.getConcerts()

                Log.d("API_RESULT", "Total artis: ${artists.size}")

                val adapter = ArtistsAdapter(artists)

                binding.rvConcert.apply {
                    layoutManager = LinearLayoutManager(this@ArtistsList)
                    this.adapter = adapter
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}", e)
                Toast.makeText(
                    this@ArtistsList,
                    "Gagal memuat data artis",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
