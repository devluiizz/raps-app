
package com.raps.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "prontuarios",
    foreignKeys = [
        ForeignKey(
            entity = Paciente::class,
            parentColumns = ["id"],
            childColumns = ["pacienteId"]
        ),
        ForeignKey(
            entity = Caps::class,
            parentColumns = ["id"],
            childColumns = ["capsId"]
        )
    ],
    indices = [
        Index(value = ["pacienteId"], unique = true),
        Index(value = ["capsId"])
    ]
)
data class Prontuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val pacienteId: Long,
    val capsId: Long,
    val numero: String,
    val dataAbertura: String,
    val status: String,
    val observacoes: String
)
