package com.example.toucan_vinyl.Bookmarks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.toucan_vinyl.databinding.ItemBookmarkBinding

class BookmarksAdapter(
    context: Context,
    private val  Bookmarks: List<BookmarksModel>
) : ArrayAdapter<BookmarksModel>(context, 0, Bookmarks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemBookmarkBinding = ItemBookmarkBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = Bookmarks[position]

        Glide.with(context)
            .load(data.artistCover)
            .into(binding.artistImg)

        binding.artistName.text = data.artistName
        binding.liveDate.text = data.liveDate
        binding.liveLocation.text = data.liveLocation

        return view
    }
}