package com.example.toucan_vinyl.Data.Model

data class ConcertModel(
    val id: Long? = null,
    val nama_artist: String,
    val nama_album: String,
    val poster_artist: String,
    val deskripsi_artist: String,
    val nama_tour_konser: String,
    val tanggal_konser: String,
    val lokasi_negara: String,
    val lokasi_kota: String,
    val lokasi_tempat: String,
    val created_at: String? = null
)
