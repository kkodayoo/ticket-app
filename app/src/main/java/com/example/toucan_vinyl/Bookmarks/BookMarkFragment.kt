package com.example.toucan_vinyl.Bookmarks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toucan_vinyl.Home.tugasPertemuan4.DetailArtist
import com.example.toucan_vinyl.R
import com.example.toucan_vinyl.databinding.FragmentBookMarkBinding

class BookMarkFragment : Fragment() {
    private var _binding: FragmentBookMarkBinding? = null
    private val binding get() = _binding!!
    private val bookmarkList = listOf(
        BookmarksModel(
            "Ado",
            "12 Januari 2025",
            "Tokyo Dome, Jepang",
            "https://static.wikia.nocookie.net/nicodougasingers/images/b/b8/2025.01_%281_-_v1%29.jpg/revision/latest/scale-to-width-down/1000?cb=20250126182908"
        ),
        BookmarksModel(
            "King Gnu",
            "20 Januari 2025",
            "Osaka-Jo Hall, Jepang",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkECkEG_cTnu1Dxe2WhUa2QiRGo_MjjiqwsQ&s"
        ),
        BookmarksModel(
            "Back Number",
            "5 Februari 2025",
            "Saitama Super Arena, Jepang",
            "https://i.scdn.co/image/ab6761610000e5eb3f895ff0b36b97f6aba648a7"
        ),
        BookmarksModel(
            "A\$AP Rocky",
            "18 Februari 2025",
            "Madison Square Garden, USA",
            "https://i.scdn.co/image/ab6761610000e5eb5c58c41a506a0d6b32cc6cad"
        ),
        BookmarksModel(
            "Anri",
            "25 Februari 2025",
            "Nippon Budokan, Jepang",
            "https://i.scdn.co/image/ab67616d00001e02cfd93d36fe2365f9436587d1"
        ),
        BookmarksModel(
            "Kenshi Yonezu",
            "2 Maret 2025",
            "Kyocera Dome, Jepang",
            "https://i.scdn.co/image/ab67616d0000b273a52ffa7b1ee67275e32d04bc"
        ),
        BookmarksModel(
            "Rionos",
            "8 Maret 2025",
            "Zepp Osaka Bayside, Jepang",
            R.drawable.rionos
        ),
        BookmarksModel(
            "Aimer",
            "15 Maret 2025",
            "Osaka-Jo Hall, Jepang",
            R.drawable.aimer
        ),
        BookmarksModel(
            "Yorushika",
            "22 Maret 2025",
            "Sendai Sun Plaza, Jepang",
            R.drawable.yorushika
        ),
        BookmarksModel(
            "YOASOBI",
            "29 Maret 2025",
            "Tokyo Dome, Jepang",
            R.drawable.yoasobi
        ),
        BookmarksModel(
            "Yeat",
            "5 April 2025",
            "Toronto Arena, Canada",
            R.drawable.yeat
        ),
        BookmarksModel(
            "Ken Carson",
            "12 April 2025",
            "Las Vegas Sphere, USA",
            R.drawable.ken_carson
        ),
        BookmarksModel(
            "J. Cole",
            "19 April 2025",
            "Staples Center, USA",
            R.drawable.j_cole
        ),
        BookmarksModel(
            "Arctic Monkeys",
            "25 April 2025",
            "SoFi Stadium, USA",
            R.drawable.arcticmonkey505
        ),
        BookmarksModel(
            "Playboi Carti",
            "3 Mei 2025",
            "Austin Music Hall, USA",
            R.drawable.playboi_carti
        ),
        BookmarksModel(
            "EGOIST",
            "24 Mei 2025",
            "Makuhari Messe, Jepang",
            R.drawable.egoist
        ),
        BookmarksModel(
            "Radiohead",
            "31 Mei 2025",
            "Wembley Stadium, UK",
            R.drawable.radiohead
        ),
        BookmarksModel(
            "RADWIMPS",
            "7 Juni 2025",
            "Nagoya Dome, Jepang",
            R.drawable.radwimps
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for requireContext() fragment
        _binding = FragmentBookMarkBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Bookmarks"
        }
        val adapter = BookmarksAdapter(requireContext(), bookmarkList)
        binding.listBookmarksItems.adapter = adapter
        binding.listBookmarksItems.setOnItemClickListener { _, _, position, _ ->
            val selected = bookmarkList[position]
            val intent = Intent(requireContext(), DetailArtist::class.java)
            startActivity(intent)
        }
    }
}