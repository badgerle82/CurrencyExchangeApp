package com.example.currencyexchangeapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchangeapp.data.local.db.entity.Exchange
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchange(event: Exchange)


    @Query("SELECT count FROM exchanges WHERE id = 0")
    fun observeExchangesCount(): Flow<Int>
}