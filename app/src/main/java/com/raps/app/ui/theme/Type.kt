@file:OptIn(ExperimentalTextApi::class)

package com.raps.app.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.raps.app.R

// Plus Jakarta Sans e Manrope são distribuídas como fontes de eixo variável (peso "wght"),
// então um único arquivo .ttf cobre qualquer peso. Em vez de depender de instâncias estáticas
// por peso, cada família abaixo trava exatamente o peso pedido pelo DESIGN.md via FontVariation.

private val PlusJakartaSansExtraBold = FontFamily(
    Font(
        resId = R.font.plus_jakarta_sans,
        weight = FontWeight.ExtraBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(800)),
    ),
)

private val ManropeMedium = FontFamily(
    Font(
        resId = R.font.manrope,
        weight = FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontVariation.weight(500)),
    ),
)

private val ManropeBold = FontFamily(
    Font(
        resId = R.font.manrope,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700)),
    ),
)

/**
 * Um TextStyle por linha das tabelas de tipografia do DESIGN.md. Use estes tokens ao montar
 * telas em vez de declarar FontFamily/tamanho manualmente, para que a hierarquia visual
 * definida no documento se mantenha igual em telas feitas por pessoas diferentes da equipe.
 *
 * Como usar em uma tela:
 *   import com.raps.app.ui.theme.RapsTypography
 *
 *   Text(text = "Prontuários", style = RapsTypography.h2)
 *   Text(text = "Atualizado há 2 dias", style = RapsTypography.bodyRegular12)
 *
 * Componentes padrão do Material 3 (Button, TopAppBar, etc.) já herdam a tipografia correta
 * automaticamente via MaterialTheme.typography, configurado em Theme.kt — só é preciso usar
 * RapsTypography diretamente em Text() ou em componentes fora do Material 3.
 */
object RapsTypography {
    val h1 = TextStyle(fontFamily = PlusJakartaSansExtraBold, fontWeight = FontWeight.ExtraBold, fontSize = 44.sp)
    val h2 = TextStyle(fontFamily = PlusJakartaSansExtraBold, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
    val h3 = TextStyle(fontFamily = PlusJakartaSansExtraBold, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
    val h4 = TextStyle(fontFamily = PlusJakartaSansExtraBold, fontWeight = FontWeight.ExtraBold, fontSize = 21.sp)
    val h5 = TextStyle(fontFamily = PlusJakartaSansExtraBold, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)

    val button = TextStyle(fontFamily = ManropeBold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    val bodyRegular14 = TextStyle(fontFamily = ManropeMedium, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    val bodyRegular12 = TextStyle(fontFamily = ManropeMedium, fontWeight = FontWeight.Medium, fontSize = 12.sp)
    val bodyRegular10 = TextStyle(fontFamily = ManropeMedium, fontWeight = FontWeight.Medium, fontSize = 10.sp)
    val bold18 = TextStyle(fontFamily = ManropeBold, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    val bold14 = TextStyle(fontFamily = ManropeBold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    val bold12 = TextStyle(fontFamily = ManropeBold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
}
