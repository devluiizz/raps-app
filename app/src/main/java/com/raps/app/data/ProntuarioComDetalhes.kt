package com.raps.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class ProntuarioComDetalhes(
    @Embedded
    val prontuario: Prontuario,

    @Relation(
        parentColumn = "pacienteId",
        entityColumn = "id",
        entity = Paciente::class
    )
    val paciente: Paciente,

    @Relation(
        parentColumn = "capsId",
        entityColumn = "id",
        entity = Caps::class
    )
    val caps: Caps
)
