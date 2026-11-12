package com.raps.app.presentation.model

data class PacienteMock(
    val nome: String,
    val iniciais: String,
    val numeroProntuario: String,
    val ultimaVisita: String,
    val etiquetas: List<String>,
    val nascimento: String,
    val cpf: String,
    val rg: String,
    val telefone: String,
    val mae: String,
    val pai: String,
    val email: String,
    val endereco: String,
    val registrosRecentes: List<RegistroMock>,
)

data class RegistroMock(
    val servico: String,
    val profissional: String,
    val data: String,
    val descricao: String,
    val autor: String,
)
