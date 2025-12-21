package com.example.toucan_vinyl.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.toucan_vinyl.Data.dao.PaymentDao
import com.example.toucan_vinyl.Data.entity.PaymentEntity


@Database(
    entities = [PaymentEntity::class], // tambahkan entitas baru di sini
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun paymentDao(): PaymentDao
    /*Tambahkan Dao baru disini */

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}