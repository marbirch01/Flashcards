package pl.marbirch.fiszki2.data.model.domain

sealed class GameEvent {
    data class Check(val userAnswer: UserAnswer): GameEvent()
    data class Next(val correctAnswers: Int = 0, val alreadyShownCards: Int = 0): GameEvent()
}