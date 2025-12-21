package com.example.toucan_vinyl.Data.Api

import com.example.toucan_vinyl.Data.Model.TicketModel
import retrofit2.Response
import retrofit2.http.*

interface TicketApiService {

    // Ambil semua tiket
    @GET("tickets")
    suspend fun getAllTickets(
        @Query("select") select: String = "*"
    ): Response<List<TicketModel>>

    // Ambil tiket berdasarkan ticket_code (SCAN QR)
    @GET("tickets")
    suspend fun getTicketByCode(
        @Query("ticket_code") ticketCode: String,
        @Query("select") select: String = "*"
    ): Response<List<TicketModel>>

    // Insert tiket (PESAN TIKET)
    @POST("tickets")
    suspend fun createTicket(
        @Body ticket: TicketModel
    ): Response<Unit>
}
