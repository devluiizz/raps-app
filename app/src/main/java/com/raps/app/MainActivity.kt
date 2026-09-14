package com.raps.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raps.app.ui.theme.RapsAppTheme
import com.raps.app.ui.theme.RapsBgLight
import com.raps.app.ui.theme.RapsTypography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapsAppTheme {
                HomePlaceholder()
            }
        }
    }
}

// Tela provisória: existe apenas para comprovar visualmente que a paleta e a tipografia do
// DESIGN.md estão carregando corretamente. Deve ser substituída assim que a primeira
// funcionalidade real (Agenda, Prontuários ou Gestão de Funcionários) for definida.
@Composable
private fun HomePlaceholder(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(RapsBgLight)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(R.string.app_name), style = RapsTypography.h1)
            Text(text = stringResource(R.string.home_placeholder_subtitle), style = RapsTypography.bodyRegular14)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePlaceholderPreview() {
    RapsAppTheme {
        HomePlaceholder()
    }
}
