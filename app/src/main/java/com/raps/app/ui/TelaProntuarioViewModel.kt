package com.raps.app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TelaProntuarioViewModel : ViewModel() {
    data class UiState(val mostrarTodosDetalhes: Boolean = true)

    private val _estadoDaTela = MutableStateFlow(UiState())
    val estadoDaTela: StateFlow<UiState> = _estadoDaTela.asStateFlow()

    fun alternarDetalhes() = _estadoDaTela.update { it.copy(mostrarTodosDetalhes = !it.mostrarTodosDetalhes) }
    fun redefinirDetalhes() = _estadoDaTela.update { it.copy(mostrarTodosDetalhes = true) }
}
