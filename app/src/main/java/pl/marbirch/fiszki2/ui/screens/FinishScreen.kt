package pl.marbirch.fiszki2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.marbirch.fiszki2.data.model.domain.GameState
import pl.marbirch.fiszki2.ui.default_component.GameButton
import pl.marbirch.fiszki2.ui.theme.defaultGameColor
import pl.revolshen.fiszki.ui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun FinishScreen(gameState: GameState.Finished, modifier: Modifier = Modifier, onFinished: () -> Unit = {}) {
    Surface(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                )
            )
            .fillMaxSize(),
        color = defaultGameColor){
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.padding(top = 150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val finishText by remember {
                    val points = gameState.correctAnswers.toFloat()
                    val maxPoints = gameState.alreadyShownCards.toFloat()
                    val percent = ((points/maxPoints) * 100).roundToInt()
                    val text = when(percent){
                        in 0..10 -> "Next time!"
                        in 11..25 -> "Pretty well!"
                        in 26..50 -> "Good job!"
                        in 51..75 -> "Great job"
                        in 76..100 -> "Awesome"
                        else -> "Nice"
                    }
                    mutableStateOf(text)
                }
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Text(text = finishText.uppercase(), style = Typography.titleLarge.copy(
                    shadow = Shadow(color = Color.Gray, offset = Offset(x= 5f, y = 5f), blurRadius = 5f, )
                ), fontSize = 48.sp, color = Color.White)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(text = "${gameState.correctAnswers}/${gameState.alreadyShownCards}", style = Typography.titleLarge, color = Color.White)
            }
            GameButton(modifier = Modifier.size(300.dp, 70.dp), textStyle = Typography.labelLarge, text = "Exit", onClick = onFinished)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FinishScreenPreview() {
    val state = GameState.Finished(10, 10)
    FinishScreen(gameState = state)
}