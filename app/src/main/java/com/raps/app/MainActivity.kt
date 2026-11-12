package com.raps.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
                AnimatedContent(
                    targetState = estaLogado to pacienteSelecionado,
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = {
                        when {
                            targetState.second != null ->
                                (slideInHorizontally(initialOffsetX = { it / 5 }, animationSpec = tween(260)) +
                                    fadeIn(animationSpec = tween(220))) togetherWith
                                    (slideOutHorizontally(targetOffsetX = { -it / 8 }, animationSpec = tween(220)) +
                                        fadeOut(animationSpec = tween(160)))

                            initialState.second != null ->
                                (slideInHorizontally(initialOffsetX = { -it / 5 }, animationSpec = tween(260)) +
                                    fadeIn(animationSpec = tween(220))) togetherWith
                                    (slideOutHorizontally(targetOffsetX = { it / 8 }, animationSpec = tween(220)) +
                                        fadeOut(animationSpec = tween(160)))

                            else -> fadeIn(animationSpec = tween(240)) togetherWith
                                fadeOut(animationSpec = tween(140))
                        }
                    },
                    label = "transicao_telas",
                ) { (estaLogadoNaTela, paciente) ->
                    if (!estaLogadoNaTela) {
                        TelaLogin(aoEntrar = { estaLogado = true })
                    } else if (paciente == null) {
                        TelaPacientes(aoClicarPaciente = { pacienteSelecionado = it })
                    } else {
                        TelaProntuario(
                            paciente = paciente,
                            aoVoltar = { pacienteSelecionado = null },
                        )
                    }
                }
            }
        }
    }
}
