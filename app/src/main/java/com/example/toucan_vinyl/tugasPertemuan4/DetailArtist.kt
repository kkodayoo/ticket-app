package com.example.toucan_vinyl.tugasPertemuan4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toucan_vinyl.MainActivity
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.ActivityDetailArtistBinding

class DetailArtist : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val username = intent.getStringExtra("username")
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.getStringExtra("username")
            intent.putExtra("username", "$username")
            startActivity(intent)
        }
    }
}