package com.example.currencyexchangeapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.currencyexchangeapp.data.local.db.entity.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<Currency>)

    @Update
    suspend fun updateCurrencies(currencies: List<Currency>)

    @Query("SELECT * FROM currencies WHERE balance > 0.0")
    fun observeCurrenciesWithBalance(): Flow<List<Currency>>

    @Query("SELECT * FROM currencies WHERE name = :name")
    suspend fun getCurrencyByName(name: String): List<Currency>

    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<Currency>

    @Query("SELECT * FROM currencies")
    fun observeCurrencies(): Flow<List<Currency>>
}