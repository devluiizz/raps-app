package com.raps.app.data

import kotlinx.coroutines.flow.Flow

class CapsRepository(private val capsDao: CapsDao) {
    val caps: Flow<List<Caps>> = capsDao.listarTodos()

    suspend fun inserirCaps(caps: Caps) {
        capsDao.inserir(caps)
    }

    suspend fun deletarCaps(caps: Caps) {
        capsDao.deletar(caps)
    }
}