package com.raps.app.data

import kotlinx.coroutines.flow.Flow

class PacienteRepository(private val pacienteDao: PacienteDao) {
    val pacientes: Flow<List<Paciente>> = pacienteDao.listarTodos()

    suspend fun inserirPaciente(paciente: Paciente) {
        pacienteDao.inserir(paciente)
    }

    suspend fun deletarPaciente(paciente: Paciente) {
        pacienteDao.deletar(paciente)
    }
}