package com.example.toucan_vinyl

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toucan_vinyl.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Gunakan ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menyesuaikan padding agar tidak tertutup status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Tombol REGISTER ditekan
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            // === VALIDASI FORM ===
            when {
                username.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                    Snackbar.make(binding.root, "Semua field harus diisi", Snackbar.LENGTH_SHORT).show()
                }

                password != confirmPassword -> {
                    Snackbar.make(binding.root, "Password tidak cocok", Snackbar.LENGTH_SHORT).show()
                }

                else -> {
                    // Simpan data registrasi sederhana
                    val editor = sharedPref.edit()
                    editor.putString("username", username)
                    editor.putString("email", email)
                    editor.putString("name", name)
                    editor.putString("password", password)
                    editor.apply()

                    // Tampilkan dialog sukses
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Registrasi Berhasil")
                        .setMessage("Akun untuk $username berhasil dibuat.\nSilakan login untuk melanjutkan.")
                        .setPositiveButton("Login") { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(this, AuthActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .show()
                }
            }
        }

        // Tombol "Sudah punya akun? Klik di sini"
        binding.tvLoginHere.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
