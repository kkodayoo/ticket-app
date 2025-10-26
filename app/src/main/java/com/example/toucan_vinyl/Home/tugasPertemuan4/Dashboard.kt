package com.example.toucan_vinyl.Home.tugasPertemuan4

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.ActivityDashboardBinding
import com.example.toucan_vinyl.Home.tugasPertemuan6.JpopFragment
import com.example.toucan_vinyl.Home.tugasPertemuan6.RecommendFragment
import com.example.toucan_vinyl.Home.tugasPertemuan6.UtaFragment

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        replaceFragment(RecommendFragment())
        binding.cardGenre.setOnClickListener {
            replaceFragment(RecommendFragment())
        }

        binding.cardGenre2.setOnClickListener {
            replaceFragment(JpopFragment())
        }

        binding.cardGenre3.setOnClickListener {
            replaceFragment(UtaFragment())
        }
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
}