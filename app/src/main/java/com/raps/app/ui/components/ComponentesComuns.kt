package com.raps.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raps.app.ui.theme.RapsBorderDefault
import com.raps.app.ui.theme.RapsPrimarySoft
import com.raps.app.ui.theme.RapsPrimaryStrong
import com.raps.app.ui.theme.RapsTextSecondary
import com.raps.app.ui.theme.RapsTextPrimary
import com.raps.app.ui.theme.RapsTypography

@Composable
fun BotaoBuscaCabecalho() {
    BotaoIconeCabecalho { IconeBusca(Modifier.size(18.dp), RapsTextPrimary) }
}

@Composable
fun BotaoNotificacoesCabecalho() {
    BotaoIconeCabecalho {
        Box {
            IconeSino(Modifier.size(18.dp), RapsTextPrimary)
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

@Composable
private fun BotaoIconeCabecalho(conteudo: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .clickable { },
        contentAlignment = Alignment.Center,
    ) { conteudo() }
}

@Composable
fun IconeBusca(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val espessuraTraco = 1.8.dp.toPx()
        drawCircle(color, radius = size.minDimension * .29f, center = androidx.compose.ui.geometry.Offset(size.width * .42f, size.height * .42f), style = Stroke(espessuraTraco))
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .64f, size.height * .64f), androidx.compose.ui.geometry.Offset(size.width * .88f, size.height * .88f), espessuraTraco, cap = StrokeCap.Round)
    }
}

@Composable
private fun IconeSino(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val espessuraTraco = 1.7.dp.toPx()
        val caminho = Path().apply {
            moveTo(size.width * .22f, size.height * .72f)
            lineTo(size.width * .30f, size.height * .60f)
            lineTo(size.width * .30f, size.height * .42f)
            cubicTo(size.width * .30f, size.height * .03f, size.width * .70f, size.height * .03f, size.width * .70f, size.height * .42f)
            lineTo(size.width * .70f, size.height * .60f)
            lineTo(size.width * .80f, size.height * .72f)
            close()
        }
        drawPath(caminho, color, style = Stroke(espessuraTraco, cap = StrokeCap.Round))
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .18f, size.height * .75f), androidx.compose.ui.geometry.Offset(size.width * .82f, size.height * .75f), espessuraTraco, cap = StrokeCap.Round)
        drawArc(color, 25f, 130f, false, androidx.compose.ui.geometry.Offset(size.width * .39f, size.height * .73f), androidx.compose.ui.geometry.Size(size.width * .22f, size.height * .20f), style = Stroke(espessuraTraco))
    }
}

@Composable
fun BarraInferiorPacientes() {
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
                ItemBarraInferior("Início", "inicio")
                ItemBarraInferior("Pacientes", "paciente", selecionado = true)
                ItemBarraInferior("Agenda", "calendario")
                ItemBarraInferior("Minha agenda", "adicionarPessoa")
                ItemBarraInferior("Mais", "mais")
            }
        }
    }
}

@Composable
private fun ItemBarraInferior(rotulo: String, nomeIcone: String, selecionado: Boolean = false) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (selecionado) RapsPrimarySoft else Color.Transparent)
            .width(if (selecionado) 64.dp else 57.dp)
            .height(48.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        IconeNavegacao(nomeIcone, Modifier.size(18.dp), if (selecionado) RapsPrimaryStrong else RapsTextSecondary)
        Spacer(Modifier.height(3.dp))
        Text(
            rotulo,
            color = if (selecionado) RapsPrimaryStrong else RapsTextSecondary,
            style = RapsTypography.bodyRegular10.copy(fontSize = 8.sp),
            maxLines = 1,
        )
    }
}

@Composable
fun SetaDuplaPequena(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val caminho = Path().apply {
            moveTo(size.width * .38f, size.height * .20f)
            lineTo(size.width * .68f, size.height * .50f)
            lineTo(size.width * .38f, size.height * .80f)
        }
        drawPath(caminho, color, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
private fun IconeNavegacao(nome: String, modifier: Modifier, color: Color) {
    Canvas(modifier) {
        val estiloTraco = Stroke(width = 1.45.dp.toPx(), cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
        when (nome) {
            "inicio" -> {
                val caminho = Path().apply { moveTo(size.width * .12f, .48f * size.height); lineTo(.5f * size.width, .13f * size.height); lineTo(.88f * size.width, .48f * size.height); lineTo(.82f * size.width, .48f * size.height); lineTo(.82f * size.width, .89f * size.height); lineTo(.18f * size.width, .89f * size.height); lineTo(.18f * size.width, .48f * size.height); close() }
                drawPath(caminho, color, style = estiloTraco)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .43f, size.height * .89f), androidx.compose.ui.geometry.Offset(size.width * .43f, size.height * .62f), estiloTraco.width)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .57f, size.height * .62f), androidx.compose.ui.geometry.Offset(size.width * .57f, size.height * .89f), estiloTraco.width)
            }
            "paciente" -> {
                drawCircle(color, size.minDimension * .17f, androidx.compose.ui.geometry.Offset(size.width * .50f, size.height * .30f), style = estiloTraco)
                val caminho = Path().apply { moveTo(.14f * size.width, .84f * size.height); cubicTo(.15f * size.width, .52f * size.height, .85f * size.width, .52f * size.height, .86f * size.width, .84f * size.height); close() }
                drawPath(caminho, color, style = estiloTraco)
            }
            "calendario" -> {
                drawRoundRect(color, androidx.compose.ui.geometry.Offset(size.width * .12f, size.height * .20f), androidx.compose.ui.geometry.Size(size.width * .76f, size.height * .68f), androidx.compose.ui.geometry.CornerRadius(3.dp.toPx()), style = estiloTraco)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .12f, size.height * .40f), androidx.compose.ui.geometry.Offset(size.width * .88f, size.height * .40f), estiloTraco.width)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .32f, size.height * .10f), androidx.compose.ui.geometry.Offset(size.width * .32f, size.height * .30f), estiloTraco.width, cap = StrokeCap.Round)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .68f, size.height * .10f), androidx.compose.ui.geometry.Offset(size.width * .68f, size.height * .30f), estiloTraco.width, cap = StrokeCap.Round)
            }
            "adicionarPessoa" -> {
                drawCircle(color, size.minDimension * .15f, androidx.compose.ui.geometry.Offset(size.width * .40f, size.height * .30f), style = estiloTraco)
                val caminho = Path().apply { moveTo(.08f * size.width, .84f * size.height); cubicTo(.08f * size.width, .53f * size.height, .70f * size.width, .53f * size.height, .70f * size.width, .84f * size.height) }
                drawPath(caminho, color, style = estiloTraco)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .78f, size.height * .30f), androidx.compose.ui.geometry.Offset(size.width * .78f, size.height * .58f), estiloTraco.width, cap = StrokeCap.Round)
                drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .64f, size.height * .44f), androidx.compose.ui.geometry.Offset(size.width * .92f, size.height * .44f), estiloTraco.width, cap = StrokeCap.Round)
            }
            else -> {
                repeat(3) { indice -> drawCircle(color, 1.4.dp.toPx(), androidx.compose.ui.geometry.Offset(size.width * (.22f + .28f * indice), size.height * .50f)) }
            }
        }
    }
}

