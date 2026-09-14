package com.raps.app.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta oficial do RAPS - APP (DESIGN.md). Os nomes seguem os tokens do documento
// para que qualquer ajuste de cor possa ser conferido diretamente na tabela de origem.
// Não adicione cores fora desta lista sem antes atualizar o DESIGN.md.
//
// Como usar em uma tela:
//   import com.raps.app.ui.theme.RapsPrimary
//
//   Text(text = "Consulta", color = RapsPrimary)
//   Box(modifier = Modifier.background(RapsSurface02))
//
// Para cor de fundo/conteúdo padrão (primary, background, error...) prefira
// MaterialTheme.colorScheme.* — ele já aponta para estes tokens (ver Theme.kt) e resolve
// sozinho contraste com onPrimary/onBackground. Use os valores abaixo direto só quando
// precisar de um token que não tem equivalente no ColorScheme do Material 3, como os
// tokens de status (RapsSuccess, RapsWarning...) ou os fundos suaves (*Soft).

// Background
val RapsBgLight = Color(0xFFF2F6F5)

// Surface
val RapsSurfaceSolid = Color(0xDBFFFFFF) // rgba(255, 255, 255, 0.86)
val RapsSurfaceGlass = Color(0xE0FFFFFF) // rgba(255, 255, 255, 0.88)
val RapsSurface02 = Color(0xFFEDF3F1)
val RapsSurface03 = Color(0xFFE5EEEB)

// Brand
val RapsPrimarySoft = Color(0xFFDCEFEB)
val RapsPrimary = Color(0xFF1F7A6C)
val RapsPrimaryStrong = Color(0xFF125E53)
val RapsAccent = Color(0xFF7A62D3)
val RapsAccentSoft = Color(0xFFEEE9FF)

// Texto e borda
val RapsTextPrimary = Color(0xFF17312D)
val RapsTextSecondary = Color(0xFF6F837F)
val RapsBorderDefault = Color(0x1F1F4C44) // rgba(31, 76, 68, 0.12)

// Status: cada estado tem uma cor forte (texto/ícone) e um fundo suave (chip/container).
val RapsSuccess = Color(0xFF2B8A67)
val RapsSuccessSoft = Color(0xFFDFF4E9)
val RapsWarning = Color(0xFFBD7B24)
val RapsWarningSoft = Color(0xFFFFF0D4)
val RapsDanger = Color(0xFFC9535F)
val RapsDangerSoft = Color(0xFFFCE3E6)
val RapsInfo = Color(0xFF347DA0)
val RapsInfoSoft = Color(0xFFE1F1F8)
