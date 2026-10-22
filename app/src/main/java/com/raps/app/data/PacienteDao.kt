package com.raps.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PacienteDao {
    @Query("SELECT * FROM pacientes")
    fun listarTodos(): Flow<List<Paciente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(paciente: Paciente)

    @Delete
    suspend fun deletar(paciente: Paciente)

    @Transaction
    @Query("SELECT * FROM pacientes")
    fun listarComProntuario(): Flow<List<PacienteComProntuario>>

}