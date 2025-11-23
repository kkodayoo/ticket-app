package com.example.toucan_vinyl.Home.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toucan_vinyl.Data.Model.ConcertModel
import com.example.toucan_vinyl.databinding.ItemArtistsBinding

class ArtistsAdapter(
    private val items: List<ConcertModel>
) : RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {

    inner class ArtistsViewHolder(val binding: ItemArtistsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        val binding = ItemArtistsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtistsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        // Set data text
        holder.binding.tvArtist.text = item.nama_artist
        holder.binding.tvDesc.text = item.deskripsi_artist  // Tambahkan ini

        val poster = item.poster_artist

        // Cek apakah gambar dari URL atau drawable
        when {
            poster.startsWith("http") -> {
                Glide.with(context)
                    .load(poster)
                    .into(holder.binding.imgPoster)
            }
            poster.startsWith("drawable://") -> {
                val drawableName = poster.removePrefix("drawable://")
                val resId = context.resources.getIdentifier(
                    drawableName,
                    "drawable",
                    context.packageName
                )
                if (resId != 0)
                    holder.binding.imgPoster.setImageResource(resId)
            }
            else -> {
                holder.binding.imgPoster.setImageResource(
                    android.R.color.darker_gray
                )
            }
        }
    }


    override fun getItemCount(): Int = items.size
}
