package com.example.toucan_vinyl

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.toucan_vinyl.Bookmarks.BookMarkFragment
import com.example.toucan_vinyl.Home.HomeFragment
import com.example.toucan_vinyl.Home.artist.ArtistsListFragment
import com.example.toucan_vinyl.User.Payment.PaymentFragment
import com.example.toucan_vinyl.Wiki.WikiFragment
import com.example.toucan_vinyl.databinding.ActivityBaseBinding


class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.bookmarks -> {
                    Toast.makeText(this, "Bookmarks Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.ticket -> {
                    Toast.makeText(this, "Ticket List Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.wiki -> {
                    Toast.makeText(this, "Wiki Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.user -> {
                    Toast.makeText(this, "User Menu Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false // return false jika item tidak ada yang di klik
            }
        }
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.ticket -> {
                    replaceFragment(ArtistsListFragment())
                    true
                }

                R.id.bookmarks -> {
                    replaceFragment(BookMarkFragment())
                    true
                }

                R.id.wiki -> {
                    replaceFragment(WikiFragment())
                    true
                }

                R.id.user -> {
                    replaceFragment(PaymentFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> ini kita nonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}