package com.example.toucan_vinyl.User.Scans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toucan_vinyl.Data.Model.TicketModel
import com.example.toucan_vinyl.databinding.ItemTicketBinding

class TicketListAdapter(
    private val items: List<TicketModel>,
    private val onQrClick: (TicketModel) -> Unit
) : RecyclerView.Adapter<TicketListAdapter.VH>() {

    inner class VH(val binding: ItemTicketBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemTicketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ticket = items[position]

        holder.binding.tvArtist.text = ticket.nama_artist
        holder.binding.tvConcert.text = ticket.nama_konser

        holder.binding.tvDate.text =
            "Tanggal: ${ticket.tanggal_konser}"

        holder.binding.tvLocation.text =
            listOfNotNull(
                ticket.lokasi_tempat,
                ticket.lokasi_kota,
                ticket.lokasi_negara
            ).joinToString(", ")

        holder.binding.btnShowQr.setOnClickListener {
            onQrClick(ticket)
        }
    }

    override fun getItemCount() = items.size
}
