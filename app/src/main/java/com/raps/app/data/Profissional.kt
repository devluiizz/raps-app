package com.raps.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profissionais")
data class Profissional(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val endereco: String
)