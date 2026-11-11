package com.raps.app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TelaPacientesViewModel : ViewModel() {
    data class UiState(
        val consulta: String = "",
        val filtroSelecionado: String = "Todos",
        val pacientes: List<PacienteMock> = listOf(
            PacienteMock("Jo\u00e3o da Silva", "JS", "#79", "Hoje, 10:20", listOf("Psicologia", "+2")),
            PacienteMock("Joana Braga", "JB", "#79", "Hoje, 10:20", listOf("Grupo de Mulheres", "+3")),
            PacienteMock("Samuel Jos\u00e9", "SJ", "#79", "Hoje, 10:20", listOf("Assist\u00eancia Social", "+5")),
            PacienteMock("Inacio Renan", "IR", "#79", "Hoje, 10:20", listOf("Psicologia", "Grupo de Homens")),
            PacienteMock("Pedro Jos\u00e9", "PJ", "#79", "Hoje, 10:20", listOf("Grupo de Homens", "+2")),
        ),
    ) {
        val pacientesVisiveis: List<PacienteMock>
            get() = pacientes.filter { paciente ->
                val consultaNormalizada = consulta.trim()
                val correspondeConsulta = consultaNormalizada.isBlank() ||
                    paciente.nome.contains(consultaNormalizada, ignoreCase = true) ||
                    paciente.numeroProntuario.contains(consultaNormalizada, ignoreCase = true)
                val correspondeFiltro = filtroSelecionado == "Todos" || paciente.etiquetas.any { it == filtroSelecionado }
                correspondeConsulta && correspondeFiltro
            }

        companion object {
            val filtros = listOf("Todos", "Psicologia", "Grupo de Mulheres", "Assist\u00eancia Social", "Grupo de Homens")
        }
    }

    private val _estadoDaTela = MutableStateFlow(UiState())
    val estadoDaTela: StateFlow<UiState> = _estadoDaTela.asStateFlow()

    fun aoAlterarConsulta(valor: String) = _estadoDaTela.update { it.copy(consulta = valor) }
    fun aoSelecionarFiltro(valor: String) = _estadoDaTela.update { it.copy(filtroSelecionado = valor) }
    fun redefinirFiltros() = _estadoDaTela.update { it.copy(consulta = "", filtroSelecionado = "Todos") }
}
