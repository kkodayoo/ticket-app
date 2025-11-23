package com.example.toucan_vinyl.Home.artist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toucan_vinyl.Data.Api.ConcertApiClient
import com.example.toucan_vinyl.Data.Model.ConcertModel
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.ActivityArtistDetailBinding
import kotlinx.coroutines.launch

class ArtistsListFragment : Fragment() {

    private var _binding: ActivityArtistDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar setup
        (requireActivity() as? androidx.appcompat.app.AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                title = "Daftar Artist"
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        }

        loadArtistsFromApi()
    }

    private fun loadArtistsFromApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val artists: List<ConcertModel> = ConcertApiClient.apiService.getConcerts()
                Log.d("API_RESULT", "Total artis: ${artists.size}")

                val adapter = ArtistsAdapter(artists)
                binding.rvConcert.layoutManager = LinearLayoutManager(requireContext())
                binding.rvConcert.adapter = adapter

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Error: ${e.message}")
                Toast.makeText(
                    requireContext(),
                    "Gagal memuat data artis",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
