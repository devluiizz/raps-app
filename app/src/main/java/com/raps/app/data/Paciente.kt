package com.raps.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacientes")
data class Paciente(
    @PrimaryKey(autoGenerate = false)
    val prontuario: String,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val endereco: String
)