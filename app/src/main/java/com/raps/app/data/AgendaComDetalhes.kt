package com.raps.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class AgendaComDetalhes(
    @Embedded
    val agenda: Agenda,

    @Relation(
        parentColumn = "pacienteId",
        entityColumn = "id",
        entity = Paciente::class
    )
    val paciente: Paciente,

    @Relation(
        parentColumn = "profissionalId",
        entityColumn = "id",
        entity = Profissional::class
    )
    val profissional: Profissional
)
