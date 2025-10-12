package com.example.toucan_vinyl


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toucan_vinyl.databinding.ActivityMainBinding
import com.example.toucan_vinyl.tugasPertemuan2.RumusBangunan
import com.example.toucan_vinyl.tugasPertemuan3.LoginScreen
import com.example.toucan_vinyl.tugasPertemuan4.Dashboard
import com.example.toucan_vinyl.tugasPertemuan4.DetailArtist
import com.example.toucan_vinyl.tugasPertemuan5.WebViewActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Main Activity"
            subtitle = "Tombol Akses Halaman"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        binding.webviewButton.setOnClickListener {
            val i = Intent(this, WebViewActivity::class.java)
            startActivity(i)
        }
        val username = intent.getStringExtra("username")
        binding.logoutButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    Log.e("Info Dialog","Anda memilih Ya!")
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                    Log.e("Info Dialog","Anda memilih Tidak!")
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_LONG)
                        .setAction("Tutup"){
                            Log.e("Info Snackbar","Snackbar ditutup")
                        }
                        .show()
                    Log.e("Info Snackbar","Snackbar dibuka")
                }
                .show()
        }
        binding.custom1Button.setOnClickListener {
            val intent = Intent(this, DetailArtist::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
        binding.custom2Button.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
        binding.bangunanButton.setOnClickListener {
            val intent = Intent(this, RumusBangunan::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
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

            R.id.action_search -> {
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_settings -> {
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_webview -> {
                val i = Intent(this, WebViewActivity::class.java)
                startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}