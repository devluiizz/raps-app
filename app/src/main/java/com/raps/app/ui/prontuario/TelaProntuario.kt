package com.raps.app.ui.prontuario
import com.raps.app.presentation.model.PacienteMock
import com.raps.app.ui.components.BarraInferiorPacientes
import com.raps.app.ui.components.SetaDuplaPequena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
fun TelaProntuario(paciente: PacienteMock, aoVoltar: () -> Unit, modeloTelaProntuario: TelaProntuarioViewModel = viewModel()) {
    val estadoDaTela by modeloTelaProntuario.estadoDaTela.collectAsStateWithLifecycle()
    val mostrarTodosDetalhes = estadoDaTela.mostrarTodosDetalhes
    DisposableEffect(modeloTelaProntuario) {
        onDispose { modeloTelaProntuario.redefinirDetalhes() }
    }
    val servicosConhecidos = paciente.etiquetas.filterNot { it.startsWith("+") }

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
            CabecalhoProntuario(paciente = paciente, aoVoltar = aoVoltar)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 14.dp,
                    bottom = 24.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                item { CartaoResumoPaciente(paciente) }
                item {
                    SecaoDetalhes(titulo = "ACOMPANHAMENTO", subtitulo = "Serviços e grupos") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            servicosConhecidos.chunked(2).forEach { servicosDaLinha ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    servicosDaLinha.forEach { EtiquetaServico(it) }
                                }
                            }
                            if (servicosConhecidos.isEmpty()) {
                                Text("Nenhum serviço informado", color = RapsTextSecondary, style = RapsTypography.bodyRegular12)
                            }
                        }
                    }
                }
                item {
                    SecaoDetalhes(
                        titulo = "DADOS CADASTRAIS",
                        subtitulo = "Informações essenciais",
                        acao = if (mostrarTodosDetalhes) "Ocultar" else "Ver detalhes",
                        aoAcionar = modeloTelaProntuario::alternarDetalhes,
                    ) {
                        if (mostrarTodosDetalhes) {
                            Column {
                                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                                    CampoDetalhe("Nascimento", paciente.nascimento, Modifier.weight(1f))
                                    CampoDetalhe("CPF", paciente.cpf, Modifier.weight(1f))
                                }
                                DivisorDetalhe()
                                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                                    CampoDetalhe("RG", paciente.rg, Modifier.weight(1f))
                                    CampoDetalhe("Telefone", paciente.telefone, Modifier.weight(1f))
                                }
                                DivisorDetalhe()
                                CampoDetalhe("Mãe", paciente.mae)
                                DivisorDetalhe()
                                CampoDetalhe("Pai", paciente.pai)
                                DivisorDetalhe()
                                CampoDetalhe("E-mail", paciente.email)
                                DivisorDetalhe()
                                CampoDetalhe("Endereço", paciente.endereco)
                            }
                        }
                    }
                }
                item {
                    SecaoDetalhes(
                        titulo = "PRONTUÁRIO",
                        subtitulo = "Linha do tempo multiprofissional",
                        acao = "+ Registrar",
                        aoAcionar = { },
                    ) {
                        if (paciente.registrosRecentes.isNotEmpty()) Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            paciente.registrosRecentes.forEachIndexed { indice, registro ->
                                ItemLinhaDoTempo(
                                    servico = registro.servico,
                                    profissional = registro.profissional,
                                    data = registro.data,
                                    descricao = registro.descricao,
                                    autor = registro.autor,
                                    ehPrimeiro = indice == 0,
                                )
                            }
                        } else {
                            Text(
                                "Nenhum registro recente informado",
                                color = RapsTextSecondary,
                                style = RapsTypography.bodyRegular12,
                                modifier = Modifier.padding(vertical = 8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CabecalhoProntuario(paciente: PacienteMock, aoVoltar: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White)
                    .clickable(onClick = aoVoltar),
                contentAlignment = Alignment.Center,
            ) { IconeSetaVoltar(Modifier.size(18.dp), RapsTextPrimary) }
            Spacer(Modifier.width(11.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = paciente.nome,
                    color = RapsTextPrimary,
                    style = RapsTypography.h5.copy(fontSize = 17.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Prontuário ${paciente.numeroProntuario}  •  Acompanhamento ativo",
                    color = RapsTextSecondary,
                    style = RapsTypography.bodyRegular10.copy(fontSize = 9.sp),
                    maxLines = 1,
                )
            }
            Spacer(Modifier.width(6.dp))
            SetaDuplaPequena(Modifier.size(18.dp), RapsTextSecondary)
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
    }
}

@Composable
private fun CartaoResumoPaciente(paciente: PacienteMock) {
    val quantidadeServicosAtivos = paciente.etiquetas.sumOf { etiqueta ->
        if (etiqueta.startsWith("+")) etiqueta.removePrefix("+").toIntOrNull() ?: 0 else 1
    }
    val quantidadeRegistrosRecentes = paciente.registrosRecentes.size
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(RapsPrimarySoft),
                contentAlignment = Alignment.Center,
            ) {
                Text(paciente.iniciais, color = RapsPrimaryStrong, style = RapsTypography.bold18)
            }
            Spacer(Modifier.width(11.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(Modifier.size(7.dp).clip(CircleShape).background(Color(0xFF2B8A67)))
                    Text("Acompanhamento ativo", color = Color(0xFF2B8A67), style = RapsTypography.bold12)
                }
                Text(
                    "Prontuário ${paciente.numeroProntuario}  •  identificação interna",
                    color = RapsTextSecondary,
                    style = RapsTypography.bodyRegular10,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AcaoResumo("+", "Nova evolução", primario = true, modifier = Modifier.weight(1f))
            AcaoResumo("▦", "Agendar", primario = false, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EstatisticaResumo(quantidadeServicosAtivos.toString(), "serviços e grupos ativos", Modifier.weight(1f))
            EstatisticaResumo(quantidadeRegistrosRecentes.toString(), "registros recentes exibidos", Modifier.weight(1f))
        }
    }
}

@Composable
private fun AcaoResumo(simbolo: String, rotulo: String, primario: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (primario) RapsPrimary else RapsPrimarySoft)
            .clickable { }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(simbolo, color = if (primario) Color.White else RapsPrimaryStrong, style = RapsTypography.bold14)
        Spacer(Modifier.width(6.dp))
        Text(
            rotulo,
            color = if (primario) Color.White else RapsPrimaryStrong,
            style = RapsTypography.bold12,
            maxLines = 1,
        )
    }
}

@Composable
private fun EstatisticaResumo(valor: String, rotulo: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(RapsSurface02)
            .padding(horizontal = 10.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(valor, color = RapsPrimaryStrong, style = RapsTypography.bold18)
        Spacer(Modifier.width(7.dp))
        Text(rotulo, color = RapsTextSecondary, style = RapsTypography.bodyRegular10, lineHeight = 13.sp)
    }
}

@Composable
private fun SecaoDetalhes(
    titulo: String,
    subtitulo: String,
    acao: String? = null,
    aoAcionar: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(titulo, color = RapsPrimary, style = RapsTypography.bold12.copy(fontSize = 9.sp))
                Text(subtitulo, color = RapsTextPrimary, style = RapsTypography.h5.copy(fontSize = 16.sp))
            }
            if (acao != null) {
                Text(
                    acao,
                    color = RapsPrimary,
                    style = RapsTypography.bold12,
                    modifier = Modifier
                        .clip(RoundedCornerShape(9.dp))
                        .clickable(onClick = aoAcionar)
                        .padding(horizontal = 9.dp, vertical = 7.dp),
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .padding(14.dp),
        ) { content() }
    }
}

@Composable
private fun EtiquetaServico(rotulo: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(RapsAccentSoft)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(RapsAccent))
        Text(rotulo, color = RapsAccent, style = RapsTypography.bodyRegular12)
    }
}

@Composable
private fun CampoDetalhe(rotulo: String, valor: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 9.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(rotulo, color = RapsTextSecondary, style = RapsTypography.bodyRegular10)
        Text(valor, color = RapsTextPrimary, style = RapsTypography.bodyRegular12)
    }
}

@Composable
private fun DivisorDetalhe() {
    Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
}

@Composable
private fun ItemLinhaDoTempo(
    servico: String,
    profissional: String,
    data: String,
    descricao: String,
    autor: String,
    ehPrimeiro: Boolean = false,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.width(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .padding(top = 4.dp)
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(if (ehPrimeiro) RapsPrimary else RapsSurface03),
            )
            Box(
                Modifier
                    .width(1.dp)
                    .height(154.dp)
                    .background(RapsBorderDefault),
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(RapsBgLight)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    Modifier.clip(RoundedCornerShape(6.dp)).background(RapsPrimarySoft)
                        .padding(horizontal = 7.dp, vertical = 4.dp),
                ) { Text(servico, color = RapsPrimaryStrong, style = RapsTypography.bold12.copy(fontSize = 10.sp)) }
                Text(data, color = RapsTextSecondary, style = RapsTypography.bodyRegular10, maxLines = 1)
            }
            Text(profissional, color = RapsTextPrimary, style = RapsTypography.bold14)
            Text(descricao, color = RapsTextSecondary, style = RapsTypography.bodyRegular12, lineHeight = 17.sp)
            DivisorDetalhe()
            Text(autor, color = RapsTextSecondary, style = RapsTypography.bodyRegular10, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun IconeSetaVoltar(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val espessuraTraco = 1.8.dp.toPx()
        drawLine(
            color,
            androidx.compose.ui.geometry.Offset(size.width * .78f, size.height * .5f),
            androidx.compose.ui.geometry.Offset(size.width * .22f, size.height * .5f),
            espessuraTraco,
            cap = StrokeCap.Round,
        )
        drawLine(
            color,
            androidx.compose.ui.geometry.Offset(size.width * .22f, size.height * .5f),
            androidx.compose.ui.geometry.Offset(size.width * .47f, size.height * .24f),
            espessuraTraco,
            cap = StrokeCap.Round,
        )
        drawLine(
            color,
            androidx.compose.ui.geometry.Offset(size.width * .22f, size.height * .5f),
            androidx.compose.ui.geometry.Offset(size.width * .47f, size.height * .76f),
            espessuraTraco,
            cap = StrokeCap.Round,
        )
    }
}

