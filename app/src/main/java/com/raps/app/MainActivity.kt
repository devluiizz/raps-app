package com.raps.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.raps.app.presentation.model.PacienteMock
import com.raps.app.ui.login.TelaLogin
import com.raps.app.ui.pacientes.TelaPacientes
import com.raps.app.ui.prontuario.TelaProntuario
import com.raps.app.ui.theme.RapsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapsAppTheme {
                var estaLogado by remember { mutableStateOf(false) }
                var pacienteSelecionado by remember { mutableStateOf<PacienteMock?>(null) }
                BackHandler(enabled = estaLogado && pacienteSelecionado != null) { pacienteSelecionado = null }
                if (!estaLogado) {
                    TelaLogin(aoEntrar = { estaLogado = true })
                } else if (pacienteSelecionado == null) {
                    TelaPacientes(aoClicarPaciente = { pacienteSelecionado = it })
                } else {
                    TelaProntuario(
                        paciente = pacienteSelecionado!!,
                        aoVoltar = { pacienteSelecionado = null },
                    )
                }
            }
        }
    }
}
