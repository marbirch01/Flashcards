package pl.marbirch.fiszki2.data.model.domain

import pl.marbirch.fiszki2.data.model.Card

sealed class GameState{
    data object NotStarted : GameState()
    data object Loading : GameState()

    data class Running(
        val currentCard: Card,
        val correctAnswers: Int = 0,
        val alreadyShownCards: Int = 0
    ) : GameState()

    data class Answered(
        val userAnswerStr: String = "",
        val isCorrect: Boolean,
        val currentCard: Card,
        val correctAnswers: Int = 0,
        val alreadyShownCards: Int = 0
    ) : GameState()

    data class Finished(
        val correctAnswers: Int = 0,
        val alreadyShownCards: Int = 0
    ) : GameState()
}
