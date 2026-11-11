package com.raps.app.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun TelaProntuario(patient: Patient, onBack: () -> Unit) {
    var showAllDetails by remember { mutableStateOf(true) }
    val isReferencePatient = patient.name == "João da Silva"
    val knownServices = if (isReferencePatient) {
        listOf("Psicologia", "Grupo de Homens", "Assistência Social")
    } else {
        patient.tags.filterNot { it.startsWith("+") }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = RapsBgLight,
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = { PatientBottomBar() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.statusBars)
                .background(RapsBgLight),
        ) {
            DetailsHeader(patient = patient, onBack = onBack)
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
                item { PatientOverviewCard(patient) }
                item {
                    DetailsSection(title = "ACOMPANHAMENTO", subtitle = "Serviços e grupos") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            knownServices.chunked(2).forEach { rowServices ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    rowServices.forEach { ServiceChip(it) }
                                }
                            }
                            if (knownServices.isEmpty()) {
                                Text("Nenhum serviço informado", color = RapsTextSecondary, style = RapsTypography.bodyRegular12)
                            }
                        }
                    }
                }
                item {
                    DetailsSection(
                        title = "DADOS CADASTRAIS",
                        subtitle = "Informações essenciais",
                        action = if (showAllDetails) "Ocultar" else "Ver detalhes",
                        onAction = { showAllDetails = !showAllDetails },
                    ) {
                        if (showAllDetails) {
                            Column {
                                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                                    DetailField("Nascimento", if (isReferencePatient) "08/11/1991" else "Não informado", Modifier.weight(1f))
                                    DetailField("CPF", if (isReferencePatient) "111.222.333-44" else "Não informado", Modifier.weight(1f))
                                }
                                DetailDivider()
                                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                                    DetailField("RG", if (isReferencePatient) "00.111.222-3" else "Não informado", Modifier.weight(1f))
                                    DetailField("Telefone", if (isReferencePatient) "(35) 99999-9999" else "Não informado", Modifier.weight(1f))
                                }
                                DetailDivider()
                                DetailField("Mãe", if (isReferencePatient) "Regina Moreira da Silva" else "Não informado")
                                DetailDivider()
                                DetailField("Pai", if (isReferencePatient) "Marcos Antonio Fernandes Silva" else "Não informado")
                                DetailDivider()
                                DetailField("E-mail", if (isReferencePatient) "joaosilva@gmail.com.br" else "Não informado")
                                DetailDivider()
                                DetailField("Endereço", if (isReferencePatient) "Rua Joãozinho, 46, Conjunto, Poços de Caldas" else "Não informado")
                            }
                        }
                    }
                }
                item {
                    DetailsSection(
                        title = "PRONTUÁRIO",
                        subtitle = "Linha do tempo multiprofissional",
                        action = "+ Registrar",
                        onAction = { },
                    ) {
                        if (isReferencePatient) Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            TimelineEntry(
                                service = "Psicologia",
                                professional = "Maria Silva",
                                date = "17/08/2026  •  10:20",
                                description = "Atendimento individual registrado para acompanhamento do plano terapêutico. Paciente participou da escuta e foram alinhados próximos passos.",
                                author = "Registrado por Maria Silva  •  Psicóloga  •  CRP fictício",
                                isFirst = true,
                            )
                            TimelineEntry(
                                service = "Assistência Social",
                                professional = "Ana Souza",
                                date = "16/08/2026  •  14:05",
                                description = "Registro de acompanhamento social e atualização das orientações institucionais pertinentes ao atendimento.",
                                author = "Registrado por Ana Souza  •  Assistente Social",
                            )
                            TimelineEntry(
                                service = "Grupo de Homens",
                                professional = "Paulo Lima",
                                date = "14/08/2026  •  14:00",
                                description = "Participação em atividade coletiva. Atividade registrada no prontuário multiprofissional.",
                                author = "Registrado por Paulo Lima  •  Terapeuta ocupacional",
                            )
                            TimelineEntry(
                                service = "Psiquiatria",
                                professional = "Dr. Lucas",
                                date = "12/08/2026  •  09:30",
                                description = "Consulta de acompanhamento registrada. Conteúdo demonstrativo, sem informações médicas reais.",
                                author = "Registrado por Dr. Lucas  •  Médico  •  CRM fictício",
                            )
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
private fun DetailsHeader(patient: Patient, onBack: () -> Unit) {
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
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) { BackArrowGlyph(Modifier.size(18.dp), RapsTextPrimary) }
            Spacer(Modifier.width(11.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = patient.name,
                    color = RapsTextPrimary,
                    style = RapsTypography.h5.copy(fontSize = 17.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Prontuário ${patient.record}  •  Acompanhamento ativo",
                    color = RapsTextSecondary,
                    style = RapsTypography.bodyRegular10.copy(fontSize = 9.sp),
                    maxLines = 1,
                )
            }
            Spacer(Modifier.width(6.dp))
            TinyChevron(Modifier.size(18.dp), RapsTextSecondary)
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
    }
}

@Composable
private fun PatientOverviewCard(patient: Patient) {
    val activeServiceCount = patient.tags.sumOf { tag ->
        if (tag.startsWith("+")) tag.removePrefix("+").toIntOrNull() ?: 0 else 1
    }
    val recentRecordCount = if (patient.name == "João da Silva") 4 else 0
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
                Text(patient.initials, color = RapsPrimaryStrong, style = RapsTypography.bold18)
            }
            Spacer(Modifier.width(11.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(Modifier.size(7.dp).clip(CircleShape).background(Color(0xFF2B8A67)))
                    Text("Acompanhamento ativo", color = Color(0xFF2B8A67), style = RapsTypography.bold12)
                }
                Text(
                    "Prontuário ${patient.record}  •  identificação interna",
                    color = RapsTextSecondary,
                    style = RapsTypography.bodyRegular10,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OverviewAction("+", "Nova evolução", primary = true, modifier = Modifier.weight(1f))
            OverviewAction("▦", "Agendar", primary = false, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OverviewStat(activeServiceCount.toString(), "serviços e grupos ativos", Modifier.weight(1f))
            OverviewStat(recentRecordCount.toString(), "registros recentes exibidos", Modifier.weight(1f))
        }
    }
}

@Composable
private fun OverviewAction(symbol: String, label: String, primary: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (primary) RapsPrimary else RapsPrimarySoft)
            .clickable { }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(symbol, color = if (primary) Color.White else RapsPrimaryStrong, style = RapsTypography.bold14)
        Spacer(Modifier.width(6.dp))
        Text(
            label,
            color = if (primary) Color.White else RapsPrimaryStrong,
            style = RapsTypography.bold12,
            maxLines = 1,
        )
    }
}

@Composable
private fun OverviewStat(value: String, label: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(RapsSurface02)
            .padding(horizontal = 10.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(value, color = RapsPrimaryStrong, style = RapsTypography.bold18)
        Spacer(Modifier.width(7.dp))
        Text(label, color = RapsTextSecondary, style = RapsTypography.bodyRegular10, lineHeight = 13.sp)
    }
}

@Composable
private fun DetailsSection(
    title: String,
    subtitle: String,
    action: String? = null,
    onAction: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(title, color = RapsPrimary, style = RapsTypography.bold12.copy(fontSize = 9.sp))
                Text(subtitle, color = RapsTextPrimary, style = RapsTypography.h5.copy(fontSize = 16.sp))
            }
            if (action != null) {
                Text(
                    action,
                    color = RapsPrimary,
                    style = RapsTypography.bold12,
                    modifier = Modifier
                        .clip(RoundedCornerShape(9.dp))
                        .clickable(onClick = onAction)
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
private fun ServiceChip(label: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(RapsAccentSoft)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(RapsAccent))
        Text(label, color = RapsAccent, style = RapsTypography.bodyRegular12)
    }
}

@Composable
private fun DetailField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 9.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(label, color = RapsTextSecondary, style = RapsTypography.bodyRegular10)
        Text(value, color = RapsTextPrimary, style = RapsTypography.bodyRegular12)
    }
}

@Composable
private fun DetailDivider() {
    Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
}

@Composable
private fun TimelineEntry(
    service: String,
    professional: String,
    date: String,
    description: String,
    author: String,
    isFirst: Boolean = false,
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
                    .background(if (isFirst) RapsPrimary else RapsSurface03),
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
                ) { Text(service, color = RapsPrimaryStrong, style = RapsTypography.bold12.copy(fontSize = 10.sp)) }
                Text(date, color = RapsTextSecondary, style = RapsTypography.bodyRegular10, maxLines = 1)
            }
            Text(professional, color = RapsTextPrimary, style = RapsTypography.bold14)
            Text(description, color = RapsTextSecondary, style = RapsTypography.bodyRegular12, lineHeight = 17.sp)
            DetailDivider()
            Text(author, color = RapsTextSecondary, style = RapsTypography.bodyRegular10, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun BackArrowGlyph(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val stroke = 1.8.dp.toPx()
        drawLine(
            color,
            androidx.compose.ui.geometry.Offset(size.width * .78f, size.height * .5f),
            androidx.compose.ui.geometry.Offset(size.width * .22f, size.height * .5f),
            stroke,
            cap = StrokeCap.Round,
        )
        drawLine(
            color,
            androidx.compose.ui.geometry.Offset(size.width * .22f, size.height * .5f),
            androidx.compose.ui.geometry.Offset(size.width * .47f, size.height * .24f),
            stroke,
            cap = StrokeCap.Round,
        )
        drawLine(
            color,
            androidx.compose.ui.geometry.Offset(size.width * .22f, size.height * .5f),
            androidx.compose.ui.geometry.Offset(size.width * .47f, size.height * .76f),
            stroke,
            cap = StrokeCap.Round,
        )
    }
}

