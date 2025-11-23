package com.example.toucan_vinyl.Home.concert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toucan_vinyl.Data.Model.ConcertModel
import com.example.toucan_vinyl.databinding.ItemConcertnormalBinding

class ConcertAdapter(
    private val items: List<ConcertModel>
) : RecyclerView.Adapter<ConcertAdapter.ConcertViewHolder>() {

    inner class ConcertViewHolder(val binding: ItemConcertnormalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcertViewHolder {
        val binding = ItemConcertnormalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ConcertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConcertViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        // Set text
        holder.binding.tvArtist.text = item.nama_artist
        holder.binding.tvLocation.text =
            "${item.lokasi_tempat}, ${item.lokasi_kota}, ${item.lokasi_negara}"
        holder.binding.tvDate.text = item.tanggal_konser

        val poster = item.poster_artist

        // Jika poster adalah URL
        if (poster.startsWith("http")) {
            Glide.with(context)
                .load(poster)
                .into(holder.binding.imgPoster)

            // Jika poster adalah drawable, format: drawable://nama_drawable
        } else if (poster.startsWith("drawable://")) {

            // Ambil nama drawable
            val drawableName = poster.removePrefix("drawable://")

            val drawableResId = context.resources.getIdentifier(
                drawableName,
                "drawable",
                context.packageName
            )

            if (drawableResId != 0) {
                holder.binding.imgPoster.setImageResource(drawableResId)
            } else {
                holder.binding.imgPoster.setImageResource(android.R.color.darker_gray)
            }

            // Jika format tidak dikenali, fallback
        } else {
            holder.binding.imgPoster.setImageResource(android.R.color.darker_gray)
        }
    }

    override fun getItemCount(): Int = items.size
}
