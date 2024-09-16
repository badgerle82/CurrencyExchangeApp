package com.example.currencyexchangeapp.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey val name: String,
    val balance: Double,
    val rate: Double
)