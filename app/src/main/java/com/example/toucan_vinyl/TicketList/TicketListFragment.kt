package com.example.toucan_vinyl.TicketList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.FragmentTicketListBinding

class TicketListFragment : Fragment() {

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!
    private val dataList = listOf(
        "Ado",
        "King Gnu",
        "Back Number",
        "Natori",
        "A\$AP Rocky",
        "Anri",
        "Kenshi Yonezu",
        "Rionos",
        "Aimer",
        "Yorushika",
        "YOASOBI",
        "Yeat",
        "Ken Carson",
        "J. Cole",
        "Kendrick Lamar",
        "Trippie Redd",
        "Twenty One Pilots",
        "21 Savage",
        "EGOIST",
        "Radiohead",
        "RADWIMPS"
    )
    private val dataListWithDate = listOf(
        mapOf("title" to "Ado", "date" to "12 Januari 2025", "location" to "Tokyo Dome, Jepang"),
        mapOf(
            "title" to "King Gnu",
            "date" to "20 Januari 2025",
            "location" to "Osaka-Jo Hall, Jepang"
        ),
        mapOf(
            "title" to "Back Number",
            "date" to "5 Februari 2025",
            "location" to "Saitama Super Arena, Jepang"
        ),
        mapOf(
            "title" to "Natori",
            "date" to "10 Februari 2025",
            "location" to "Zepp Nagoya, Jepang"
        ),
        mapOf(
            "title" to "A\$AP Rocky",
            "date" to "18 Februari 2025",
            "location" to "Madison Square Garden, USA"
        ),
        mapOf(
            "title" to "Anri",
            "date" to "25 Februari 2025",
            "location" to "Nippon Budokan, Jepang"
        ),
        mapOf(
            "title" to "Kenshi Yonezu",
            "date" to "2 Maret 2025",
            "location" to "Kyocera Dome, Jepang"
        ),
        mapOf(
            "title" to "Rionos",
            "date" to "8 Maret 2025",
            "location" to "Zepp Osaka Bayside, Jepang"
        ),
        mapOf("title" to "Aimer", "date" to "15 Maret 2025", "location" to "Osaka-Jo Hall, Jepang"),
        mapOf(
            "title" to "Yorushika",
            "date" to "22 Maret 2025",
            "location" to "Sendai Sun Plaza, Jepang"
        ),
        mapOf("title" to "YOASOBI", "date" to "29 Maret 2025", "location" to "Tokyo Dome, Jepang"),
        mapOf("title" to "Yeat", "date" to "5 April 2025", "location" to "Toronto Arena, Canada"),
        mapOf(
            "title" to "Ken Carson",
            "date" to "12 April 2025",
            "location" to "Las Vegas Sphere, USA"
        ),
        mapOf("title" to "J. Cole", "date" to "19 April 2025", "location" to "Staples Center, USA"),
        mapOf(
            "title" to "Kendrick Lamar",
            "date" to "25 April 2025",
            "location" to "SoFi Stadium, USA"
        ),
        mapOf(
            "title" to "Trippie Redd",
            "date" to "3 Mei 2025",
            "location" to "Austin Music Hall, USA"
        ),
        mapOf(
            "title" to "Twenty One Pilots",
            "date" to "10 Mei 2025",
            "location" to "Houston Toyota Center, USA"
        ),
        mapOf(
            "title" to "21 Savage",
            "date" to "17 Mei 2025",
            "location" to "Brooklyn Mirage, USA"
        ),
        mapOf("title" to "EGOIST", "date" to "24 Mei 2025", "location" to "Makuhari Messe, Jepang"),
        mapOf("title" to "Radiohead", "date" to "31 Mei 2025", "location" to "Wembley Stadium, UK"),
        mapOf("title" to "RADWIMPS", "date" to "7 Juni 2025", "location" to "Nagoya Dome, Jepang")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "More"
        }
        val adapter = SimpleAdapter(
            requireContext(),
            dataListWithDate,
            R.layout.list_item_ticket,
            arrayOf("title", "date", "location"),
            intArrayOf(R.id.textTitle, R.id.textDate, R.id.textLocation)
        )


        // Hubungkan listViewItems dengan adapter
        binding.listViewItems.adapter = adapter

        // Tambahkan aksi saat item di-list diklik
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataListWithDate[position]
            val title = selectedItem["title"]
            val date = selectedItem["date"]
            val location = selectedItem["location"]

            Toast.makeText(requireContext(), "Kamu memilih: $title\n$date • $location", Toast.LENGTH_SHORT)
                .show()
        }
    }
}