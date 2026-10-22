package com.raps.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raps.app.data.Profissional

@Database(
    entities = [
        Paciente::class,
        Profissional::class,
        Caps::class,
        Prontuario::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pacienteDao(): PacienteDao
    abstract fun profissionalDao(): ProfissionalDao
    abstract fun capsDao(): CapsDao
    abstract fun prontuarioDao(): ProntuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "raps_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}