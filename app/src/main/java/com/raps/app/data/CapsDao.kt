package com.raps.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CapsDao {
    @Query("SELECT * FROM caps")
    fun listarTodos(): Flow<List<Caps>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(caps: Caps)

    @Delete
    suspend fun deletar(caps: Caps)
}