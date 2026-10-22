package com.raps.app.data

import kotlinx.coroutines.flow.Flow

class AgendaRepository(private val agendaDao: AgendaDao) {
    val agendas: Flow<List<Agenda>> = agendaDao.listarTodos()
    val agendasComDetalhes: Flow<List<AgendaComDetalhes>> = agendaDao.listarComDetalhes()

    fun listarPorPaciente(pacienteId: Long): Flow<List<Agenda>> =
        agendaDao.listarPorPaciente(pacienteId)

    fun listarPorProfissional(profissionalId: Long): Flow<List<Agenda>> =
        agendaDao.listarPorProfissional(profissionalId)

    suspend fun inserirAgenda(agenda: Agenda) {
        agendaDao.inserir(agenda)
    }

    suspend fun deletarAgenda(agenda: Agenda) {
        agendaDao.deletar(agenda)
    }
}
