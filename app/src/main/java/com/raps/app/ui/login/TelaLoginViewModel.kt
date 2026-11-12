package com.raps.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TelaLoginViewModel : ViewModel() {
    data class UiState(
        val enderecoEmail: String = "",
        val senha: String = "",
        val senhaVisivel: Boolean = false,
        val lembrarDispositivo: Boolean = false,
        val erroLogin: Boolean = false,
        val carregando: Boolean = false,
    )

    private val _estadoDaTela = MutableStateFlow(UiState())
    val estadoDaTela: StateFlow<UiState> = _estadoDaTela.asStateFlow()

    fun aoAlterarEmail(valor: String) = _estadoDaTela.update { it.copy(enderecoEmail = valor, erroLogin = false) }
    fun aoAlterarSenha(valor: String) = _estadoDaTela.update { it.copy(senha = valor, erroLogin = false) }
    fun alternarVisibilidadeSenha() = _estadoDaTela.update { it.copy(senhaVisivel = !it.senhaVisivel) }
    fun alternarLembrarDispositivo() = _estadoDaTela.update { it.copy(lembrarDispositivo = !it.lembrarDispositivo) }

    fun entrar(aoEntrar: () -> Unit) {
        if (_estadoDaTela.value.carregando) return
        _estadoDaTela.update { it.copy(carregando = true, erroLogin = false) }

        viewModelScope.launch {
            delay(700)
            val estado = _estadoDaTela.value
            val usuarioValido = estado.enderecoEmail.trim().equals(EMAIL_MOCK, ignoreCase = true) ||
                estado.enderecoEmail.trim() == MATRICULA_MOCK
            val credenciaisValidas = usuarioValido && estado.senha == SENHA_MOCK

            _estadoDaTela.update { it.copy(carregando = false, erroLogin = !credenciaisValidas) }
            if (credenciaisValidas) aoEntrar()
        }
    }

    private companion object {
        const val EMAIL_MOCK = "ana.souza@unidade.gov.br"
        const val MATRICULA_MOCK = "12345"
        const val SENHA_MOCK = "raps123"
    }
}
