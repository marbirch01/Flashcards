package pl.marbirch.fiszki2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.marbirch.fiszki2.ui.default_component.GameButton
import pl.marbirch.fiszki2.ui.theme.defaultStartScreenColor
import pl.revolshen.fiszki.ui.theme.Typography

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNewGame: () -> Unit = {},
    onHistory: () -> Unit = {}
    ) {
    Surface(modifier = Modifier.fillMaxSize(), color = defaultStartScreenColor) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            GameButton(
                modifier = Modifier.size(200.dp, 60.dp),
                text = "New game",
                textStyle = Typography.labelLarge,
                onClick = onNewGame)
            GameButton(
                modifier = Modifier.padding(top = 32.dp).size(200.dp, 60.dp),
                text = "History",
                textStyle = Typography.labelLarge,
                onClick = onHistory)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview() {
    //StartScreen()
}