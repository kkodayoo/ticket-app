package com.example.toucan_vinyl.Data.Api

import com.example.toucan_vinyl.Data.Model.ConcertModel
import retrofit2.http.GET

interface ConcertApiService {
    // Mengambil semua data konser dari tabel musisi
    @GET("musisi")
    suspend fun getConcerts(): List<ConcertModel>
}