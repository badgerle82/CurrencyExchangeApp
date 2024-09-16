package com.example.currencyexchangeapp.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchanges")
data class Exchange(
    @PrimaryKey
    val id: Int = 0,
    val count: Int
)