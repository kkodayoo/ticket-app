package com.example.toucan_vinyl.Data.Model

data class TicketModel(
    val ticket_code: String,
    val nama_artist: String,
    val nama_konser: String,
    val nama_festival: String?,
    val tanggal_konser: String, // format: YYYY-MM-DD
    val lokasi_negara: String?,
    val lokasi_kota: String?,
    val lokasi_tempat: String?,
    val status: String = "ACTIVE"
)
