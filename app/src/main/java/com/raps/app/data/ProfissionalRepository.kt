package com.raps.app.data

import kotlinx.coroutines.flow.Flow

class ProfissionalRepository(private val profissionalDao: ProfissionalDao) {
    val profissionais: Flow<List<Profissional>> = profissionalDao.listarTodos()

    suspend fun inserirProfissional(profissional: Profissional) {
        profissionalDao.inserir(profissional)
    }

    suspend fun deletarProfissional(profissional: Profissional) {
        profissionalDao.deletar(profissional)
    }
}