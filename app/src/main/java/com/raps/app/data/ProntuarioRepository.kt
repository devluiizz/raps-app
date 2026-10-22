package com.raps.app.data

import kotlinx.coroutines.flow.Flow

class ProntuarioRepository(private val prontuarioDao: ProntuarioDao) {
    val prontuarios: Flow<List<Prontuario>> = prontuarioDao.listarTodos()

    suspend fun inserirProntuario(prontuario: Prontuario) {
        prontuarioDao.inserir(prontuario)
    }

    suspend fun deletarProntuario(prontuario: Prontuario) {
        prontuarioDao.deletar(prontuario)
    }
}