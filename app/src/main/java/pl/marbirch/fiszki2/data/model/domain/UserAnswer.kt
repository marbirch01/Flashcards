package pl.marbirch.fiszki2.data.model.domain

import pl.marbirch.fiszki2.data.model.Card

data class UserAnswer(val card: Card, val answer: String = "", val correctAnswers: Int = 0, val alreadyShownCards: Int = 0)
