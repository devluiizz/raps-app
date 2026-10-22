package com.raps.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class PacienteComAgenda(
    @Embedded
    val paciente: Paciente,

    @Relation(
        parentColumn = "id",
        entityColumn = "pacienteId"
    )
    val agendas: List<Agenda>
)
