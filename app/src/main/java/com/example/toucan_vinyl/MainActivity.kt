package com.example.toucan_vinyl


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toucan_vinyl.databinding.ActivityMainBinding
import com.example.toucan_vinyl.tugasPertemuan2.RumusBangunan
import com.example.toucan_vinyl.tugasPertemuan3.LoginScreen
import com.example.toucan_vinyl.tugasPertemuan4.dashboard
import com.example.toucan_vinyl.tugasPertemuan4.detailArtist

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
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
        binding.custom1Button.setOnClickListener {
            val intent = Intent(this, detailArtist::class.java)
            startActivity(intent)
        }
        binding.custom2Button.setOnClickListener {
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)
        }
        binding.bangunanButton.setOnClickListener {
            val intent = Intent(this, RumusBangunan::class.java)
            startActivity(intent)
        }
    }
}