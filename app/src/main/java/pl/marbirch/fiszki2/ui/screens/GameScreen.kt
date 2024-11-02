package pl.marbirch.fiszki2.ui.screens

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.marbirch.fiszki2.data.model.Card
import pl.marbirch.fiszki2.data.model.domain.GameState
import pl.marbirch.fiszki2.data.model.domain.GameState.Answered
import pl.marbirch.fiszki2.data.model.domain.UserAnswer
import pl.marbirch.fiszki2.ui.default_component.GameButton
import pl.marbirch.fiszki2.ui.default_component.GameCard
import pl.marbirch.fiszki2.ui.theme.defaultGameColor
import pl.marbirch.fiszki2.ui.theme.defaultGameCorrectColor
import pl.marbirch.fiszki2.ui.theme.defaultGameICorrectColor
import pl.marbirch.fiszki2.ui.theme.defaultStartScreenColor
import pl.revolshen.fiszki.ui.theme.Typography

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameState: GameState = GameState.NotStarted,
    onChecked: (UserAnswer) -> Unit = {},
    onNext: (UserAnswer) -> Unit = {},
    onFinished: () -> Unit = {}
) {

    when (gameState) {
        GameState.NotStarted -> {}
        GameState.Loading -> CustomIrregularLoadingAnimation()
        is GameState.Running -> {
            GameScreenState(
                state = gameState,
                color = defaultGameColor,
                buttonText = "Check",
                onButtonClicked = {
                    val uAnswer = UserAnswer(
                        card = gameState.currentCard,
                        answer = it,
                        correctAnswers = gameState.correctAnswers,
                        alreadyShownCards = gameState.alreadyShownCards
                    )
                    onChecked(uAnswer)
                })
        }

        is Answered -> {
            GameScreenState(
                state = gameState,
                color = if (gameState.isCorrect) defaultGameCorrectColor else defaultGameICorrectColor,
                buttonText = "Next",
                userAnswer = gameState.currentCard.polish,
                onButtonClicked = {
                    val uAnswer = UserAnswer(
                        card = gameState.currentCard,
                        answer = it,
                        correctAnswers = gameState.correctAnswers,
                        alreadyShownCards = gameState.alreadyShownCards
                    )

                    onNext(uAnswer)
                })
        }

        is GameState.Finished -> {
            FinishScreen(gameState = gameState, onFinished = onFinished)
        }
    }

}


@Composable
fun GameScreenState(
    state: GameState,
    color: Color,
    buttonText: String,
    userAnswer: String = "",
    onButtonClicked: (String) -> Unit = {}
) {
    var answer by remember {
        mutableStateOf(userAnswer)
    }

    Surface(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .fillMaxSize(), color = color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            when (state) {
                is GameState.Running -> {
                    Points(state.correctAnswers, state.alreadyShownCards)
                    GameCard(
                        text = state.currentCard.spanish,
                        userAnswer = answer,
                        onValueChange = { answer = it }
                    )
                }

                is Answered -> {
                    Points(state.correctAnswers, state.alreadyShownCards)
                    GameCard(
                        text = state.currentCard.spanish,
                        userAnswer = answer,
                        textColor = if (state.isCorrect) defaultGameCorrectColor else defaultGameICorrectColor,
                        enableEditing = false
                    )
                }

                else -> {}
            }
            GameButton(
                modifier = Modifier.size(300.dp, 70.dp),
                text = buttonText,
                textStyle = Typography.labelLarge,
                onClick = { onButtonClicked(answer) })
        }
    }
}

@Composable
fun Points(correctAnswers: Int, alreadyShownCards: Int) {
    Text(
        text = "$correctAnswers/$alreadyShownCards",
        style = Typography.titleMedium,
        color = Color.White
    )
}

@Composable
fun CustomIrregularLoadingAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angel by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
            ), repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .size(50.dp)
                .rotate(angel), shape = RoundedCornerShape(25f), color = defaultGameCorrectColor
        ) {

        }
    }
}

@Preview
@Composable
private fun CustomIrregularAnimationPreview() {
    CustomIrregularLoadingAnimation()
}

@Preview
@Composable
private fun GameScreenRunningPreview() {
    GameScreen(gameState = GameState.Running(Card("kot", "el gato")))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun GameScreenCorrectPreview() {
    val state = GameState.Answered(
        userAnswerStr = "kot",
        isCorrect = true,
        currentCard = Card("kot", "el gato")
    )
    GameScreenState(
        state = state,
        color = defaultGameCorrectColor,
        buttonText = "Next",
        userAnswer = "kot"
    )
}

@Preview
@Composable
private fun GameScreenIncorrectPreview() {
    val state = GameState.Answered(
        userAnswerStr = "pies",
        isCorrect = false,
        currentCard = Card("kot", "el gato")
    )
    GameScreenState(
        state = state,
        color = defaultGameICorrectColor,
        buttonText = "next",
        userAnswer = "pies"
    )
}






















