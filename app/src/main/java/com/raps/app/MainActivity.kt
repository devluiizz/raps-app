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
import com.raps.app.ui.Patient
import com.raps.app.ui.TelaLogin
import com.raps.app.ui.TelaPacientes
import com.raps.app.ui.TelaProntuario
import com.raps.app.ui.theme.RapsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapsAppTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var selectedPatient by remember { mutableStateOf<Patient?>(null) }
                BackHandler(enabled = isLoggedIn && selectedPatient != null) { selectedPatient = null }
                if (!isLoggedIn) {
                    TelaLogin(onLogin = { isLoggedIn = true })
                } else if (selectedPatient == null) {
                    TelaPacientes(onPatientClick = { selectedPatient = it })
                } else {
                    TelaProntuario(
                        patient = selectedPatient!!,
                        onBack = { selectedPatient = null },
                    )
                }
            }
        }
    }
}