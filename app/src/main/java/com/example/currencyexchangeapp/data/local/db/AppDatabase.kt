package com.example.currencyexchangeapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyexchangeapp.data.local.db.dao.CurrencyDao
import com.example.currencyexchangeapp.data.local.db.dao.ExchangeDao
import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.data.local.db.entity.Exchange

@Database(entities = [Currency::class, Exchange::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDao

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}