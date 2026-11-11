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

private const val MOCK_LOGIN_EMAIL = "ana.souza@unidade.gov.br"
private const val MOCK_LOGIN_REGISTRATION = "12345"
private const val MOCK_LOGIN_PASSWORD = "raps123"

@Composable
fun TelaLogin(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberDevice by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        val compact = maxHeight < 760.dp
        val headerHeight = if (compact) 184.dp else 200.dp
        Box(Modifier.fillMaxWidth().height(headerHeight).background(RapsPrimary)) {
            Canvas(Modifier.fillMaxSize()) {
                drawCircle(RapsPrimarySoft.copy(alpha = .16f), radius = 112.dp.toPx(), center = androidx.compose.ui.geometry.Offset(size.width * .88f, -8.dp.toPx()), style = Stroke(30.dp.toPx()))
            }
            Row(
                modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.statusBars).padding(start = 28.dp, top = 27.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BrandMark(Modifier.size(40.dp), Color.White)
                Spacer(Modifier.width(13.dp))
                Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text("RAPS", color = Color.White, style = RapsTypography.h3.copy(fontSize = 18.sp))
                    Text("REDE DE ATENÇÃO PSICOSSOCIAL", color = Color.White.copy(alpha = .78f), style = RapsTypography.bodyRegular10.copy(fontSize = 10.sp), letterSpacing = 1.2.sp)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().imePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).offset(y = headerHeight - 34.dp)
                    .clip(RoundedCornerShape(22.dp)).background(Color.White)
                    .border(1.dp, RapsBorderDefault, RoundedCornerShape(22.dp))
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 25.dp),
            ) {
                Text("Acesso ao sistema", color = RapsTextPrimary, style = RapsTypography.h2.copy(fontSize = 22.sp, lineHeight = 28.sp))
                Text("Use as credenciais fornecidas pela sua unidade.", color = RapsTextSecondary, style = RapsTypography.bodyRegular14.copy(fontSize = 14.sp, lineHeight = 19.sp), modifier = Modifier.padding(top = 3.dp, bottom = 16.dp))

                LoginFieldLabel("E-mail ou matrícula")
                LoginInput(
                    value = email,
                    onValueChange = { email = it; loginError = false },
                    placeholder = "ex.: ana.souza@unidade.gov.br",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(top = 6.dp),
                )
                LoginFieldLabel("Senha", Modifier.padding(top = 14.dp))
                LoginInput(
                    value = password,
                    onValueChange = { password = it; loginError = false },
                    placeholder = "Digite sua senha",
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailing = { EyeGlyph(Modifier.size(22.dp).clickable { passwordVisible = !passwordVisible }, RapsTextSecondary, passwordVisible) },
                    modifier = Modifier.padding(top = 6.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 14.dp).clip(RoundedCornerShape(4.dp)).clickable { rememberDevice = !rememberDevice },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.size(18.dp).clip(RoundedCornerShape(4.dp)).border(1.5.dp, RapsTextSecondary, RoundedCornerShape(4.dp)).background(if (rememberDevice) RapsPrimary else Color.Transparent), contentAlignment = Alignment.Center) {
                        if (rememberDevice) Text("✓", color = Color.White, style = RapsTypography.bold12)
                    }
                    Text("Lembrar neste dispositivo", color = RapsTextSecondary, style = RapsTypography.bodyRegular14.copy(fontSize = 14.sp), modifier = Modifier.padding(start = 8.dp))
                }
                if (loginError) {
                    Text("E-mail/matrícula ou senha inválidos.", color = Color(0xFFC9535F), style = RapsTypography.bodyRegular12, modifier = Modifier.padding(top = 8.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp).height(52.dp).clip(RoundedCornerShape(12.dp))
                        .background(RapsPrimary).clickable {
                            val validUser = email.trim().equals(MOCK_LOGIN_EMAIL, ignoreCase = true) || email.trim() == MOCK_LOGIN_REGISTRATION
                            if (validUser && password == MOCK_LOGIN_PASSWORD) onLogin() else loginError = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text("Entrar", color = Color.White, style = RapsTypography.button.copy(fontSize = 14.sp))
                    Spacer(Modifier.width(12.dp))
                    ArrowRightGlyph(Modifier.size(18.dp), Color.White)
                }
                Row(Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Esqueci minha senha", color = RapsPrimaryStrong, style = RapsTypography.bold12.copy(fontSize = 12.sp), modifier = Modifier.clickable { })
                    Text("Primeiro acesso", color = RapsPrimaryStrong, style = RapsTypography.bold12.copy(fontSize = 12.sp), modifier = Modifier.clickable { })
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.navigationBars).padding(horizontal = 26.dp).padding(bottom = 20.dp).clip(RoundedCornerShape(12.dp)).background(RapsPrimarySoft).padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InfoGlyph(Modifier.size(19.dp), RapsPrimaryStrong)
                Text("O acesso é concedido pelo administrador da unidade. Não há cadastro público.", color = RapsPrimaryStrong, style = RapsTypography.bodyRegular12.copy(fontSize = 12.sp, lineHeight = 17.sp), modifier = Modifier.padding(start = 10.dp))
            }
        }
    }
}

@Composable
private fun LoginFieldLabel(text: String, modifier: Modifier = Modifier) {
    Text(text, color = RapsTextPrimary, style = RapsTypography.bold14.copy(fontSize = 12.sp), modifier = modifier)
}

@Composable
private fun LoginInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(11.dp)).border(1.5.dp, RapsBorderDefault, RoundedCornerShape(11.dp)).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (value.isEmpty()) Text(placeholder, color = RapsTextSecondary.copy(alpha = .7f), style = RapsTypography.bodyRegular14.copy(fontSize = 13.sp), maxLines = 1, overflow = TextOverflow.Ellipsis)
            BasicTextField(value, onValueChange, modifier = Modifier.fillMaxWidth(), singleLine = true, textStyle = RapsTypography.bodyRegular14.copy(fontSize = 13.sp, color = RapsTextPrimary), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), visualTransformation = visualTransformation)
        }
        if (trailing != null) {
            Spacer(Modifier.width(8.dp))
            trailing()
        }
    }
}

@Composable
private fun BrandMark(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val stroke = 2.dp.toPx()
        drawCircle(color, radius = size.minDimension * .46f, style = Stroke(stroke))
        drawCircle(color, radius = size.minDimension * .30f, style = Stroke(stroke))
        drawCircle(color, radius = size.minDimension * .075f)
    }
}

@Composable
private fun EyeGlyph(modifier: Modifier = Modifier, color: Color, visible: Boolean) {
    Canvas(modifier) {
        val stroke = 1.8.dp.toPx()
        val p = Path().apply {
            moveTo(size.width * .05f, size.height * .5f)
            cubicTo(size.width * .30f, size.height * .12f, size.width * .70f, size.height * .12f, size.width * .95f, size.height * .5f)
            cubicTo(size.width * .70f, size.height * .88f, size.width * .30f, size.height * .88f, size.width * .05f, size.height * .5f)
        }
        drawPath(p, color, style = Stroke(stroke, cap = StrokeCap.Round))
        drawCircle(color, size.minDimension * .18f, style = Stroke(stroke))
        if (!visible) drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .18f, size.height * .82f), androidx.compose.ui.geometry.Offset(size.width * .82f, size.height * .18f), stroke, cap = StrokeCap.Round)
    }
}

@Composable
private fun ArrowRightGlyph(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val stroke = 2.dp.toPx()
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .12f, size.height * .5f), androidx.compose.ui.geometry.Offset(size.width * .83f, size.height * .5f), stroke, cap = StrokeCap.Round)
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .57f, size.height * .22f), androidx.compose.ui.geometry.Offset(size.width * .86f, size.height * .5f), stroke, cap = StrokeCap.Round)
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .57f, size.height * .78f), androidx.compose.ui.geometry.Offset(size.width * .86f, size.height * .5f), stroke, cap = StrokeCap.Round)
    }
}

@Composable
private fun InfoGlyph(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val stroke = 1.8.dp.toPx()
        drawCircle(color, radius = size.minDimension * .44f, style = Stroke(stroke))
        drawCircle(color, radius = 1.2.dp.toPx(), center = androidx.compose.ui.geometry.Offset(size.width * .5f, size.height * .32f))
        drawLine(color, androidx.compose.ui.geometry.Offset(size.width * .5f, size.height * .46f), androidx.compose.ui.geometry.Offset(size.width * .5f, size.height * .72f), stroke, cap = StrokeCap.Round)
    }
}

