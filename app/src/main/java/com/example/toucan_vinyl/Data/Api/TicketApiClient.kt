package com.example.toucan_vinyl.Data.Api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TicketApiClient {

    private const val BASE_URL = "https://qonfkqxbvdamulcnftir.supabase.co/rest/v1/"
    private const val API_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFvbmZrcXhidmRhbXVsY25mdGlyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM4Njc5OTYsImV4cCI6MjA3OTQ0Mzk5Nn0.rrm7T1YeOcic6qaSACRQZbBkmiK0azSPSnMxLkD6bKw"

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("apikey", API_KEY)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val apiService: TicketApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TicketApiService::class.java)
    }
}