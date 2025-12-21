package com.example.toucan_vinyl.Data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.toucan_vinyl.Data.entity.PaymentEntity

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment")
    suspend fun getAll(): List<PaymentEntity>

    @Insert
    suspend fun insert(note: PaymentEntity)

    @Delete
    suspend fun delete(note: PaymentEntity)
}