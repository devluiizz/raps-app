package com.raps.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfissionalDao {
    @Query("SELECT * FROM profissionais")
    fun listarTodos(): Flow<List<Profissional>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(profissional: Profissional)

    @Delete
    suspend fun deletar(profissional: Profissional)
}