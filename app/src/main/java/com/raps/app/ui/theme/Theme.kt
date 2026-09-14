package com.raps.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// O DESIGN.md define a paleta apenas para o modo claro. Até que a equipe valide uma versão
// escura, o app permanece sempre no esquema claro, ignorando o tema do sistema — evita misturar
// cores oficiais com um modo escuro inventado.
private val RapsLightColorScheme = lightColorScheme(
    primary = RapsPrimary,
    onPrimary = Color.White,
    primaryContainer = RapsPrimarySoft,
    onPrimaryContainer = RapsPrimaryStrong,
    secondary = RapsAccent,
    onSecondary = Color.White,
    secondaryContainer = RapsAccentSoft,
    onSecondaryContainer = RapsAccent,
    background = RapsBgLight,
    onBackground = RapsTextPrimary,
    surface = RapsSurfaceSolid,
    onSurface = RapsTextPrimary,
    surfaceVariant = RapsSurface02,
    onSurfaceVariant = RapsTextSecondary,
    error = RapsDanger,
    onError = Color.White,
    errorContainer = RapsDangerSoft,
    onErrorContainer = RapsDanger,
    outline = RapsBorderDefault,
)

// Mapeia os componentes padrão do Material 3 (Button, Text, TopAppBar...) para os tokens de
// RapsTypography, garantindo que qualquer tela nova já herde a tipografia correta sem esforço.
private val RapsMaterialTypography = Typography(
    headlineLarge = RapsTypography.h1,
    headlineMedium = RapsTypography.h2,
    headlineSmall = RapsTypography.h3,
    titleLarge = RapsTypography.h4,
    titleMedium = RapsTypography.h5,
    titleSmall = RapsTypography.bold14,
    bodyLarge = RapsTypography.bodyRegular14,
    bodyMedium = RapsTypography.bodyRegular12,
    bodySmall = RapsTypography.bodyRegular10,
    labelLarge = RapsTypography.button,
    labelMedium = RapsTypography.bold12,
    labelSmall = RapsTypography.bold12,
)

@Composable
fun RapsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RapsLightColorScheme,
        typography = RapsMaterialTypography,
        content = content,
    )
}
