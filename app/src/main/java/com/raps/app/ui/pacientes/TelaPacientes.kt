package com.raps.app.ui.pacientes
import com.raps.app.presentation.model.PacienteMock
import com.raps.app.ui.components.BarraInferiorPacientes
import com.raps.app.ui.components.BotaoBuscaCabecalho
import com.raps.app.ui.components.BotaoNotificacoesCabecalho
import com.raps.app.ui.components.IconeBusca
import com.raps.app.ui.components.SetaDuplaPequena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raps.app.ui.theme.RapsAccent
import com.raps.app.ui.theme.RapsAccentSoft
import com.raps.app.ui.theme.RapsAppTheme
import com.raps.app.ui.theme.RapsBgLight
import com.raps.app.ui.theme.RapsBorderDefault
import com.raps.app.ui.theme.RapsPrimary
import com.raps.app.ui.theme.RapsPrimaryStrong
import com.raps.app.ui.theme.RapsPrimarySoft
import com.raps.app.ui.theme.RapsSurface02
import com.raps.app.ui.theme.RapsSurface03
import com.raps.app.ui.theme.RapsTextPrimary
import com.raps.app.ui.theme.RapsTextSecondary
import com.raps.app.ui.theme.RapsTypography

@Composable
fun TelaPacientes(aoClicarPaciente: (PacienteMock) -> Unit = {}, modeloTelaPacientes: TelaPacientesViewModel = viewModel()) {
    val estadoDaTela by modeloTelaPacientes.estadoDaTela.collectAsStateWithLifecycle()
    var menuFiltroExpandido by remember { mutableStateOf(false) }
    DisposableEffect(modeloTelaPacientes) {
        onDispose { modeloTelaPacientes.redefinirFiltros() }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RapsBgLight,
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = { BarraInferiorPacientes() },
    ) { preenchimentoInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(preenchimentoInterno)
                .windowInsetsPadding(WindowInsets.statusBars)
                .background(RapsBgLight),
        ) {
            CabecalhoPacientes()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 12.dp,
                    end = 12.dp,
                    top = 14.dp,
                    bottom = 18.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    CampoBusca(
                        consulta = estadoDaTela.consulta,
                        onValueChange = modeloTelaPacientes::aoAlterarConsulta,
                        quantidadeResultados = estadoDaTela.pacientesVisiveis.size,
                    )
                }
                item {
                    Text(
                        text = "F I L T R O S",
                        color = RapsPrimary,
                        style = RapsTypography.bold12.copy(fontSize = 9.sp),
                        modifier = Modifier.padding(top = 1.dp, bottom = 0.dp),
                    )
                }
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Box {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(Color.White)
                                    .clickable { menuFiltroExpandido = true }
                                    .padding(start = 12.dp, end = 9.dp, top = 5.dp, bottom = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(estadoDaTela.filtroSelecionado, color = RapsAccent, style = RapsTypography.bodyRegular10)
                                Spacer(Modifier.width(8.dp))
                                SetaDuplaPequena(modifier = Modifier.size(14.dp), color = RapsTextPrimary)
                            }
                            DropdownMenu(
                                expanded = menuFiltroExpandido,
                                onDismissRequest = { menuFiltroExpandido = false },
                                containerColor = Color.White,
                                tonalElevation = 0.dp,
                            ) {
                                TelaPacientesViewModel.UiState.filtros.forEach { filtro ->
                                    DropdownMenuItem(
                                        text = { Text(filtro, style = RapsTypography.bodyRegular12) },
                                        onClick = {
                                            modeloTelaPacientes.aoSelecionarFiltro(filtro)
                                            menuFiltroExpandido = false
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                text = "C A D A S T R O",
                                color = RapsPrimary,
                                style = RapsTypography.bold12.copy(fontSize = 9.sp),
                            )
                            Text(
                                text = "Pacientes Encontrados",
                                color = RapsTextPrimary,
                                style = RapsTypography.h5.copy(fontSize = 16.sp),
                            )
                        }
                        Text(
                            text = "+ Novo",
                            color = RapsPrimary,
                            style = RapsTypography.bold12,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { }
                                .padding(6.dp),
                        )
                    }
                }
                if (estadoDaTela.pacientesVisiveis.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum paciente encontrado",
                            color = RapsTextSecondary,
                            style = RapsTypography.bodyRegular12,
                            modifier = Modifier.padding(vertical = 18.dp),
                        )
                    }
                } else {
                    items(estadoDaTela.pacientesVisiveis, key = { it.nome }) { paciente ->
                        CartaoPaciente(paciente, onClick = { aoClicarPaciente(paciente) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CabecalhoPacientes() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .background(RapsBgLight)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(
                    text = "B U S C A  E  P R O N T U Á R I O S",
                    color = RapsPrimary,
                    style = RapsTypography.bold12.copy(fontSize = 9.sp),
                )
                Text("Pacientes", color = RapsTextPrimary, style = RapsTypography.h5.copy(fontSize = 18.sp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BotaoBuscaCabecalho()
                BotaoNotificacoesCabecalho()
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
    }
}

@Composable
private fun CampoBusca(consulta: String, onValueChange: (String) -> Unit, quantidadeResultados: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .padding(start = 11.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconeBusca(Modifier.size(17.dp), RapsTextPrimary)
        Spacer(Modifier.width(9.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (consulta.isEmpty()) {
                Text("Nome, CPF ou Prontuário", color = RapsTextSecondary, style = RapsTypography.bodyRegular12)
            }
            BasicTextField(
                value = consulta,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = RapsTypography.bodyRegular12.copy(color = RapsTextPrimary),
            )
        }
        Box(
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(RapsSurface02),
            contentAlignment = Alignment.Center,
        ) {
            Text(quantidadeResultados.toString(), color = RapsTextPrimary, style = RapsTypography.bold12)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviaListaPacientes() {
    RapsAppTheme { TelaPacientes() }
}

