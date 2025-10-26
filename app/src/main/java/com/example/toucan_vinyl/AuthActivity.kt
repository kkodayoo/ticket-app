package com.example.toucan_vinyl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toucan_vinyl.databinding.ActivityLoginScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.signinbtn.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (username.equals(password)) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()
                val intent = Intent(this, BaseActivity::class.java)
                intent.putExtra("username", "$username")
                intent.putExtra("password", "$password")
                startActivity(intent)
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Silahkan coba lagi")
                    .setNegativeButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        Log.e("Info Dialog", "Alert Dialog Ditutup")
                        Snackbar.make(
                            binding.root,
                            "Silahkan masukkan kembali user yang sesuai",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Tutup") {
                                Log.e("Info Snackbar", "Snackbar ditutup")
                            }
                            .show()
                        Log.e("Info Snackbar", "Snackbar dibuka")
                    }
                    .show()
                Log.e("Info Dialog", "Alert Dialog Berhasil Dibuka!")
            }
        }
    }
}