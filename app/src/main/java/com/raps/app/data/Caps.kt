package com.raps.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "caps")
data class Caps(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String,
    val endereco: String
)