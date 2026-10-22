package com.raps.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "agendas",
    foreignKeys = [
        ForeignKey(
            entity = Paciente::class,
            parentColumns = ["id"],
            childColumns = ["pacienteId"]
        ),
        ForeignKey(
            entity = Profissional::class,
            parentColumns = ["id"],
            childColumns = ["profissionalId"]
        )
    ],
    indices = [Index(value = ["pacienteId"]), Index(value = ["profissionalId"])]
)
data class Agenda(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val pacienteId: Long,
    val profissionalId: Long,
    val data: String,
    val horario: String,
    val atividade: String,
    val observacoes: String
)
