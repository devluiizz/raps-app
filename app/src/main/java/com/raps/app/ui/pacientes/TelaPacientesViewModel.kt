package com.raps.app.ui.pacientes
import com.raps.app.presentation.model.PacienteMock
import com.raps.app.presentation.model.RegistroMock

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
            PacienteMock("João da Silva", "JS", "#79", "Hoje, 10:20", listOf("Psicologia", "Grupo de Homens", "Assistência Social"), "08/11/1991", "111.222.333-44", "00.111.222-3", "(35) 99999-9999", "Regina Moreira da Silva", "Marcos Antonio Fernandes Silva", "joaosilva@gmail.com.br", "Rua Joãozinho, 46, Conjunto, Poços de Caldas", listOf(
                RegistroMock("Psicologia", "Maria Silva", "17/08/2026  •  10:20", "Atendimento individual registrado para acompanhamento do plano terapêutico. Paciente participou da escuta e foram alinhados próximos passos.", "Registrado por Maria Silva  •  Psicóloga  •  CRP fictício"),
                RegistroMock("Assistência Social", "Ana Souza", "16/08/2026  •  14:05", "Registro de acompanhamento social e atualização das orientações institucionais pertinentes ao atendimento.", "Registrado por Ana Souza  •  Assistente Social"),
                RegistroMock("Grupo de Homens", "Paulo Lima", "14/08/2026  •  14:00", "Participação em atividade coletiva. Atividade registrada no prontuário multiprofissional.", "Registrado por Paulo Lima  •  Terapeuta ocupacional"),
                RegistroMock("Psiquiatria", "Dr. Lucas", "12/08/2026  •  09:30", "Consulta de acompanhamento registrada. Conteúdo demonstrativo, sem informações médicas reais.", "Registrado por Dr. Lucas  •  Médico  •  CRM fictício"),
            )),
            PacienteMock("Joana Braga", "JB", "#80", "Hoje, 09:45", listOf("Grupo de Mulheres", "Psicologia", "Assistência Social"), "14/03/1987", "222.333.444-55", "12.345.678-9", "(35) 98888-1111", "Marta Braga de Oliveira", "Carlos Eduardo Braga", "joana.braga@example.com", "Rua das Acácias, 120, Centro, Poços de Caldas", listOf(
                RegistroMock("Grupo de Mulheres", "Carla Mendes", "17/08/2026  •  09:45", "Participou de roda de conversa e atividade de acolhimento. Foram combinadas novas atividades para o próximo encontro.", "Registrado por Carla Mendes  •  Psicóloga  •  CRP fictício"),
                RegistroMock("Psicologia", "Marina Costa", "15/08/2026  •  11:15", "Realizada escuta individual e revisão dos objetivos de acompanhamento.", "Registrado por Marina Costa  •  Psicóloga  •  CRP fictício"),
                RegistroMock("Assistência Social", "Ana Souza", "12/08/2026  •  13:30", "Atualizado o acompanhamento social e compartilhadas orientações sobre os serviços disponíveis.", "Registrado por Ana Souza  •  Assistente Social"),
            )),
            PacienteMock("Samuel Duarte", "SJ", "#81", "Hoje, 08:30", listOf("Assistência Social", "Psicologia", "Grupo de Homens"), "22/07/1979", "333.444.555-66", "23.456.789-0", "(35) 97777-2222", "Lúcia Maria José", "Antônio José Pereira", "samuel.jose@example.com", "Avenida Central, 55, Jardim São Paulo, Poços de Caldas", listOf(
                RegistroMock("Assistência Social", "Rafael Nunes", "17/08/2026  •  08:30", "Realizado atendimento de acompanhamento e revisadas as necessidades sociais informadas.", "Registrado por Rafael Nunes  •  Assistente Social"),
                RegistroMock("Psicologia", "Maria Silva", "13/08/2026  •  10:00", "Atendimento individual de acolhimento e acompanhamento do plano terapêutico.", "Registrado por Maria Silva  •  Psicóloga  •  CRP fictício"),
                RegistroMock("Grupo de Homens", "Paulo Lima", "10/08/2026  •  14:00", "Participou de encontro coletivo com atividade de convivência e troca de experiências.", "Registrado por Paulo Lima  •  Terapeuta ocupacional"),
            )),
            PacienteMock("Inacio Paredes", "IR", "#82", "Ontem, 16:10", listOf("Psicologia", "Grupo de Homens"), "05/12/1994", "444.555.666-77", "34.567.890-1", "(35) 96666-3333", "Sandra Renan Almeida", "Roberto Renan Almeida", "inacio.renan@example.com", "Rua Ipê Amarelo, 88, Vila Nova, Poços de Caldas", listOf(
                RegistroMock("Psicologia", "Marina Costa", "16/08/2026  •  16:10", "Realizada conversa de acompanhamento e registro das metas definidas para o próximo atendimento.", "Registrado por Marina Costa  •  Psicóloga  •  CRP fictício"),
                RegistroMock("Grupo de Homens", "Paulo Lima", "12/08/2026  •  14:00", "Participou de atividade coletiva com foco em convivência e fortalecimento de vínculos.", "Registrado por Paulo Lima  •  Terapeuta ocupacional"),
            )),
            PacienteMock("Pedro ", "PJ", "#83", "Ontem, 14:20", listOf("Grupo de Homens", "Psicologia", "Assistência Social"), "19/09/1982", "555.666.777-88", "45.678.901-2", "(35) 95555-4444", "Eliana José Santos", "Pedro Augusto José", "pedro.jose@example.com", "Rua dos Pinheiros, 203, Santa Rosália, Poços de Caldas", listOf(
                RegistroMock("Grupo de Homens", "Paulo Lima", "16/08/2026  •  14:20", "Participou do encontro semanal e contribuiu com a atividade de convivência do grupo.", "Registrado por Paulo Lima  •  Terapeuta ocupacional"),
                RegistroMock("Psicologia", "Maria Silva", "11/08/2026  •  09:20", "Atendimento individual para acompanhamento e revisão dos combinados anteriores.", "Registrado por Maria Silva  •  Psicóloga  •  CRP fictício"),
                RegistroMock("Assistência Social", "Rafael Nunes", "08/08/2026  •  15:00", "Atualizado o registro de acompanhamento social e as orientações fornecidas.", "Registrado por Rafael Nunes  •  Assistente Social"),
            )),
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
            val filtros = listOf("Todos", "Psicologia", "Grupo de Mulheres", "Assistência Social", "Grupo de Homens")
        }
    }

    private val _estadoDaTela = MutableStateFlow(UiState())
    val estadoDaTela: StateFlow<UiState> = _estadoDaTela.asStateFlow()

    fun aoAlterarConsulta(valor: String) = _estadoDaTela.update { it.copy(consulta = valor) }
    fun aoSelecionarFiltro(valor: String) = _estadoDaTela.update { it.copy(filtroSelecionado = valor) }
    fun redefinirFiltros() = _estadoDaTela.update { it.copy(consulta = "", filtroSelecionado = "Todos") }
}
