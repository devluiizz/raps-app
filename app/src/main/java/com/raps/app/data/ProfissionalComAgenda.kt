package com.raps.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class ProfissionalComAgenda(
    @Embedded
    val profissional: Profissional,

    @Relation(
        parentColumn = "id",
        entityColumn = "profissionalId"
    )
    val agendas: List<Agenda>
)
