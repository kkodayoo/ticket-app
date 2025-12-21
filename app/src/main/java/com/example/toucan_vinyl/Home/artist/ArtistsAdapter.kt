package com.example.toucan_vinyl.Home.artist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toucan_vinyl.utils.NotificationHelper
import com.example.toucan_vinyl.utils.ReminderHelper
import com.example.toucan_vinyl.Data.Api.TicketApiClient
import com.example.toucan_vinyl.Data.Model.ConcertModel
import com.example.toucan_vinyl.Data.Model.TicketModel
import com.example.toucan_vinyl.MainActivity
import com.example.toucan_vinyl.databinding.ItemArtistsBinding
import kotlinx.coroutines.*
import java.util.Calendar

class ArtistsAdapter(
    private val items: List<ConcertModel>
) : RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {

    inner class ArtistsViewHolder(
        val binding: ItemArtistsBinding
    ) : RecyclerView.ViewHolder(binding.root)

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

        // =========================
        // SET DATA MUSISI
        // =========================
        holder.binding.tvArtist.text = item.nama_artist
        holder.binding.tvDesc.text = item.deskripsi_artist

        val poster = item.poster_artist
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
                if (resId != 0) {
                    holder.binding.imgPoster.setImageResource(resId)
                }
            }
            else -> {
                holder.binding.imgPoster.setImageResource(
                    android.R.color.darker_gray
                )
            }
        }

        // =========================
        // PESAN TIKET → SUPABASE + NOTIF + REMINDER
        // =========================
        holder.binding.btnOrderTicket.setOnClickListener {

            val ticket = TicketModel(
                ticket_code = "TCK-${item.nama_artist}-${System.currentTimeMillis()}",
                nama_artist = item.nama_artist,
                nama_konser = item.nama_album,
                nama_festival = item.nama_tour_konser,
                tanggal_konser = item.tanggal_konser,
                lokasi_negara = item.lokasi_negara,
                lokasi_kota = item.lokasi_kota,
                lokasi_tempat = item.lokasi_tempat
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = TicketApiClient
                        .apiService
                        .createTicket(ticket)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // =========================
                            // 🔔 NOTIFICATION LANGSUNG
                            // =========================
                            val intent = Intent(context, MainActivity::class.java)

                            NotificationHelper.showNotification(
                                context = context,
                                title = "Tiket Berhasil Dipesan",
                                message = "Tiket konser ${item.nama_artist} berhasil dipesan",
                                intent = intent
                            )

                            // =========================
                            // ⏰ REMINDER (1 MENIT)
                            // =========================
                            val calendar = Calendar.getInstance().apply {
                                add(Calendar.MINUTE, 1)
                            }

                            ReminderHelper.setReminder(
                                context = context,
                                hour = calendar.get(Calendar.HOUR_OF_DAY),
                                minute = calendar.get(Calendar.MINUTE),
                                title = "Reminder Konser",
                                message = "Jangan lupa konser ${item.nama_artist}",
                                targetActivity = MainActivity::class.java
                            )

                            Toast.makeText(
                                context,
                                "Tiket berhasil dipesan, reminder aktif",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                context,
                                "Gagal memesan tiket",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
