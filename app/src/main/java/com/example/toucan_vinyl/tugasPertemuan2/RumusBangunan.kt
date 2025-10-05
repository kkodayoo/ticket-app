package com.example.toucan_vinyl.tugasPertemuan2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toucan_vinyl.MainActivity
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.ActivityRumusBangunanBinding

class RumusBangunan : AppCompatActivity() {
    private lateinit var binding: ActivityRumusBangunanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRumusBangunanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Persegi
        val panjangPersegi: EditText = findViewById(R.id.panjangPersegi)
        val lebarPersegi: EditText = findViewById(R.id.lebarPersegi)
        val hasilHitungPersegi: EditText = findViewById(R.id.hasilHitungPersegi)
        val confirmPersegi: Button = findViewById(R.id.confirmPersegi)

        // Lingkaran
        val lingkaranJarijari: EditText = findViewById(R.id.lingkaranJariJari)
        val lingkaranHasil: EditText = findViewById(R.id.lingkaranHasil)
        val lingkaranConfirm: Button = findViewById(R.id.lingkaranConfirm)

        confirmPersegi.setOnClickListener {
            try {
                val panjang = panjangPersegi.text.toString().toInt()
                val lebar = lebarPersegi.text.toString().toInt()
                val luas = panjang * lebar

                hasilHitungPersegi.setText(luas.toString())

                Log.e("Klik confirmPersegi", "Luas persegi panjang = $luas")
                Toast.makeText(this, "Luas persegi panjang: $luas", Toast.LENGTH_SHORT).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        lingkaranConfirm.setOnClickListener {
            try {
                val jarijari = lingkaranJarijari.text.toString().toDouble()

                val luas = Math.PI * jarijari * jarijari

                lingkaranHasil.setText(luas.toString())

                Log.d("Klik lingkaranConfirm", "Luas lingkaran = $luas")
                Toast.makeText(this, "Luas lingkaran: $luas", Toast.LENGTH_SHORT).show()

            } catch (d: NumberFormatException) {
                Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}