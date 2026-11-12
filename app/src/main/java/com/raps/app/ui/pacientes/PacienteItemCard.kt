package com.raps.app.ui.pacientes
import com.raps.app.presentation.model.PacienteMock
import com.raps.app.ui.components.SetaDuplaPequena

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raps.app.ui.theme.RapsAccent
import com.raps.app.ui.theme.RapsAccentSoft
import com.raps.app.ui.theme.RapsSurface02
import com.raps.app.ui.theme.RapsTextPrimary
import com.raps.app.ui.theme.RapsTextSecondary
import com.raps.app.ui.theme.RapsTypography
@Composable
fun CartaoPaciente(paciente: PacienteMock, onClick: () -> Unit = {}) {
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
            Text(paciente.iniciais, color = RapsTextPrimary, style = RapsTypography.bold18.copy(fontSize = 18.sp))
        }
        Spacer(Modifier.width(9.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = paciente.nome,
                color = RapsTextPrimary,
                style = RapsTypography.bold12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "Prontuário ${paciente.numeroProntuario} • ${paciente.ultimaVisita}",
                color = RapsTextSecondary,
                style = RapsTypography.bodyRegular10.copy(fontSize = 9.sp),
                maxLines = 1,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                paciente.etiquetas.forEach { etiqueta ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(RapsAccentSoft)
                            .padding(horizontal = 5.dp, vertical = 1.dp),
                    ) {
                        Text(etiqueta, color = RapsAccent, style = RapsTypography.bodyRegular10.copy(fontSize = 8.sp), maxLines = 1)
                    }
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        SetaDuplaPequena(Modifier.size(18.dp), RapsTextPrimary)
    }
}

