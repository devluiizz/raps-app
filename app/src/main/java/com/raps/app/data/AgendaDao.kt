package com.raps.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agendas")
    fun listarTodos(): Flow<List<Agenda>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(agenda: Agenda)

    @Delete
    suspend fun deletar(agenda: Agenda)

    @Transaction
    @Query("SELECT * FROM agendas")
    fun listarComDetalhes(): Flow<List<AgendaComDetalhes>>

    @Query("SELECT * FROM agendas WHERE pacienteId = :pacienteId")
    fun listarPorPaciente(pacienteId: Long): Flow<List<Agenda>>

    @Query("SELECT * FROM agendas WHERE profissionalId = :profissionalId")
    fun listarPorProfissional(profissionalId: Long): Flow<List<Agenda>>
}
