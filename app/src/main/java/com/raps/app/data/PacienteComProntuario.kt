package com.raps.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class PacienteComProntuario(
    @Embedded
    val paciente: Paciente,

    @Relation(
        parentColumn = "id",
        entityColumn = "pacienteId"
    )
    val prontuario: Prontuario?
)
