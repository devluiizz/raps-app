package com.raps.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProntuarioDao {
    @Query("SELECT * FROM prontuarios")
    fun listarTodos(): Flow<List<Prontuario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(prontuario: Prontuario)

    @Delete
    suspend fun deletar(prontuario: Prontuario)

    @Transaction
    @Query("SELECT * FROM prontuarios")
    fun listarComDetalhes(): Flow<List<ProntuarioComDetalhes>>

    @Transaction
    @Query("SELECT * FROM prontuarios WHERE id = :id")
    suspend fun buscarComDetalhes(id: Long): ProntuarioComDetalhes?
}