package com.raps.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapsAppTheme {
                var selectedPatient by remember { mutableStateOf<Patient?>(null) }
                BackHandler(enabled = selectedPatient != null) { selectedPatient = null }
                if (selectedPatient == null) {
                    PatientListScreen(onPatientClick = { selectedPatient = it })
                } else {
                    PatientDetailsScreen(
                        patient = selectedPatient!!,
                        onBack = { selectedPatient = null },
                    )
                }
            }
        }
    }
}

private data class Patient(
    val name: String,
    val initials: String,
    val record: String,
    val lastVisit: String,
    val tags: List<String>,
)

private val samplePatients = listOf(
    Patient("João da Silva", "JS", "#79", "Hoje, 10:20", listOf("Psicologia", "+2")),
    Patient("Joana Braga", "JB", "#79", "Hoje, 10:20", listOf("Grupo de Mulheres", "+3")),
    Patient("Samuel José", "SJ", "#79", "Hoje, 10:20", listOf("Assistência Social", "+5")),
    Patient("Inacio Renan", "IR", "#79", "Hoje, 10:20", listOf("Psicologia", "Grupo de Homens")),
    Patient("Pedro José", "PJ", "#79", "Hoje, 10:20", listOf("Grupo de Homens", "+2")),
)

@Composable
private fun PatientListScreen(onPatientClick: (Patient) -> Unit = {}) {
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var filterMenuExpanded by remember { mutableStateOf(false) }

    val filters = listOf("Todos", "Psicologia", "Grupo de Mulheres", "Assistência Social", "Grupo de Homens")
    val visiblePatients = samplePatients.filter { patient ->
        val matchesQuery = query.isBlank() || patient.name.contains(query.trim(), ignoreCase = true) ||
            patient.record.contains(query.trim(), ignoreCase = true)
        val matchesFilter = selectedFilter == "Todos" || patient.tags.any { it == selectedFilter }
        matchesQuery && matchesFilter
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
            PatientHeader()

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
                    SearchField(
                        value = query,
                        onValueChange = { query = it },
                        resultCount = visiblePatients.size,
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
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color.White)
                                .clickable { filterMenuExpanded = true }
                                .padding(start = 12.dp, end = 9.dp, top = 5.dp, bottom = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(selectedFilter, color = RapsAccent, style = RapsTypography.bodyRegular10)
                            Spacer(Modifier.width(8.dp))
                            TinyChevron(modifier = Modifier.size(14.dp), color = RapsTextPrimary)
                        }
                        DropdownMenu(
                            expanded = filterMenuExpanded,
                            onDismissRequest = { filterMenuExpanded = false },
                        ) {
                            filters.forEach { filter ->
                                DropdownMenuItem(
                                    text = { Text(filter, style = RapsTypography.bodyRegular12) },
                                    onClick = {
                                        selectedFilter = filter
                                        filterMenuExpanded = false
                                    },
                                )
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
                if (visiblePatients.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum paciente encontrado",
                            color = RapsTextSecondary,
                            style = RapsTypography.bodyRegular12,
                            modifier = Modifier.padding(vertical = 18.dp),
                        )
                    }
                } else {
                    items(visiblePatients, key = { it.name }) { patient ->
                        PatientCard(patient, onClick = { onPatientClick(patient) })
                    }
                }
            }
        }
    }
}

@Composable
private fun PatientDetailsScreen(patient: Patient, onBack: () -> Unit) {
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

@Composable
private fun PatientHeader() {
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
                HeaderIconButton { SearchGlyph(Modifier.size(18.dp), RapsTextPrimary) }
                HeaderIconButton {
                    Box {
                        BellGlyph(Modifier.size(18.dp), RapsTextPrimary)
                        Box(
                            Modifier
                                .align(Alignment.TopEnd)
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC9535F)),
                        )
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
    }
}

@Composable
private fun HeaderIconButton(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .clickable { },
        contentAlignment = Alignment.Center,
    ) { content() }
}

@Composable
private fun SearchField(value: String, onValueChange: (String) -> Unit, resultCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .padding(start = 11.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SearchGlyph(Modifier.size(17.dp), RapsTextPrimary)
        Spacer(Modifier.width(9.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (value.isEmpty()) {
                Text("Nome, CPF ou Prontuário", color = RapsTextSecondary, style = RapsTypography.bodyRegular12)
            }
            BasicTextField(
                value = value,
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
            Text(resultCount.toString(), color = RapsTextPrimary, style = RapsTypography.bold12)
        }
    }
}

@Composable
private fun PatientCard(patient: Patient, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(69.dp)
            .clip(RoundedCornerShape(17.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(horizontal = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(RapsSurface02),
            contentAlignment = Alignment.Center,
        ) {
            Text(patient.initials, color = RapsTextPrimary, style = RapsTypography.bold18.copy(fontSize = 18.sp))
        }
        Spacer(Modifier.width(9.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = patient.name,
                color = RapsTextPrimary,
                style = RapsTypography.bold12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "Prontuário ${patient.record} • ${patient.lastVisit}",
                color = RapsTextSecondary,
                style = RapsTypography.bodyRegular10.copy(fontSize = 9.sp),
                maxLines = 1,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                patient.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(RapsAccentSoft)
                            .padding(horizontal = 5.dp, vertical = 1.dp),
                    ) {
                        Text(tag, color = RapsAccent, style = RapsTypography.bodyRegular10.copy(fontSize = 8.sp), maxLines = 1)
                    }
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        TinyChevron(Modifier.size(18.dp), RapsTextPrimary)
    }
}

@Composable
private fun PatientBottomBar() {
    Surface(color = Color.White, shadowElevation = 0.dp) {
        Column(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(RapsBorderDefault))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(63.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                BottomItem("Início", "home")
                BottomItem("Pacientes", "patient", selected = true)
                BottomItem("Agenda", "calendar")
                BottomItem("Minha agenda", "personAdd")
                BottomItem("Mais", "more")
            }
        }
    }
}

@Composable
private fun BottomItem(label: String, icon: String, selected: Boolean = false) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) RapsPrimarySoft else Color.Transparent)
            .width(if (selected) 64.dp else 57.dp)
            .height(48.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        NavigationGlyph(icon, Modifier.size(18.dp), if (selected) RapsPrimaryStrong else RapsTextSecondary)
        Spacer(Modifier.height(3.dp))
        Text(
            label,
            color = if (selected) RapsPrimaryStrong else RapsTextSecondary,
            style = RapsTypography.bodyRegular10.copy(fontSize = 8.sp),
            maxLines = 1,
        )
    }
}

@Composable
private fun SearchGlyph(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val stroke = 1.8.dp.toPx()
        drawCircle(color, radius = size.minDimension * .29f, center = androidx.compose.ui.geometry.Offset(size.width * .42f, size.height * .42f), style = Stroke(stroke))
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .64f, size.height * .64f), androidx.compose.ui.geometry.Offset(size.width * .88f, size.height * .88f), stroke, cap = StrokeCap.Round)
    }
}

@Composable
private fun BellGlyph(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val stroke = 1.7.dp.toPx()
        val p = Path().apply {
            moveTo(size.width * .22f, size.height * .72f)
            lineTo(size.width * .30f, size.height * .60f)
            lineTo(size.width * .30f, size.height * .42f)
            cubicTo(size.width * .30f, size.height * .03f, size.width * .70f, size.height * .03f, size.width * .70f, size.height * .42f)
            lineTo(size.width * .70f, size.height * .60f)
            lineTo(size.width * .80f, size.height * .72f)
            close()
        }
        drawPath(p, color, style = Stroke(stroke, cap = StrokeCap.Round))
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .18f, size.height * .75f), androidx.compose.ui.geometry.Offset(size.width * .82f, size.height * .75f), stroke, cap = StrokeCap.Round)
        drawArc(color, 25f, 130f, false, androidx.compose.ui.geometry.Offset(size.width * .39f, size.height * .73f), androidx.compose.ui.geometry.Size(size.width * .22f, size.height * .20f), style = Stroke(stroke))
    }
}

@Composable
private fun TinyChevron(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val p = Path().apply {
            moveTo(size.width * .38f, size.height * .20f)
            lineTo(size.width * .68f, size.height * .50f)
            lineTo(size.width * .38f, size.height * .80f)
        }
        drawPath(p, color, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
private fun NavigationGlyph(name: String, modifier: Modifier, color: Color) {
    Canvas(modifier) {
        val s = Stroke(width = 1.45.dp.toPx(), cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
        when (name) {
            "home" -> {
                val p = Path().apply { moveTo(size.width * .12f, .48f * size.height); lineTo(.5f * size.width, .13f * size.height); lineTo(.88f * size.width, .48f * size.height); lineTo(.82f * size.width, .48f * size.height); lineTo(.82f * size.width, .89f * size.height); lineTo(.18f * size.width, .89f * size.height); lineTo(.18f * size.width, .48f * size.height); close() }
                drawPath(p, color, style = s)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .43f, size.height * .89f), androidx.compose.ui.geometry.Offset(size.width * .43f, size.height * .62f), s.width)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .57f, size.height * .62f), androidx.compose.ui.geometry.Offset(size.width * .57f, size.height * .89f), s.width)
            }
            "patient" -> {
                drawCircle(color, size.minDimension * .17f, androidx.compose.ui.geometry.Offset(size.width * .50f, size.height * .30f), style = s)
                val p = Path().apply { moveTo(.14f * size.width, .84f * size.height); cubicTo(.15f * size.width, .52f * size.height, .85f * size.width, .52f * size.height, .86f * size.width, .84f * size.height); close() }
                drawPath(p, color, style = s)
            }
            "calendar" -> {
                drawRoundRect(color, androidx.compose.ui.geometry.Offset(size.width * .12f, size.height * .20f), androidx.compose.ui.geometry.Size(size.width * .76f, size.height * .68f), androidx.compose.ui.geometry.CornerRadius(3.dp.toPx()), style = s)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .12f, size.height * .40f), androidx.compose.ui.geometry.Offset(size.width * .88f, size.height * .40f), s.width)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .32f, size.height * .10f), androidx.compose.ui.geometry.Offset(size.width * .32f, size.height * .30f), s.width, cap = StrokeCap.Round)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .68f, size.height * .10f), androidx.compose.ui.geometry.Offset(size.width * .68f, size.height * .30f), s.width, cap = StrokeCap.Round)
            }
            "personAdd" -> {
                drawCircle(color, size.minDimension * .15f, androidx.compose.ui.geometry.Offset(size.width * .40f, size.height * .30f), style = s)
                val p = Path().apply { moveTo(.08f * size.width, .84f * size.height); cubicTo(.08f * size.width, .53f * size.height, .70f * size.width, .53f * size.height, .70f * size.width, .84f * size.height) }
                drawPath(p, color, style = s)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .78f, size.height * .30f), androidx.compose.ui.geometry.Offset(size.width * .78f, size.height * .58f), s.width, cap = StrokeCap.Round)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .64f, size.height * .44f), androidx.compose.ui.geometry.Offset(size.width * .92f, size.height * .44f), s.width, cap = StrokeCap.Round)
            }
            else -> {
                repeat(3) { index -> drawCircle(color, 1.4.dp.toPx(), androidx.compose.ui.geometry.Offset(size.width * (.22f + .28f * index), size.height * .50f)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PatientListPreview() {
    RapsAppTheme { PatientListScreen() }
}
