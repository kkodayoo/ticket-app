package com.example.toucan_vinyl.Data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cardNumber: String,
    val cardHolder: String,
    val cvvCVC: String,
    val createdAt: Long
)