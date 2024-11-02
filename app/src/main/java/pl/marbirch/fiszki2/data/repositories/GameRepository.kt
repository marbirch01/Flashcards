package pl.marbirch.fiszki2.data.repositories

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.marbirch.fiszki2.data.model.Card
import pl.marbirch.fiszki2.data.model.domain.GameState
import pl.marbirch.fiszki2.data.model.domain.UserAnswer
import pl.marbirch.fiszki2.data.model.dto.GameHistoryDto

class GameRepository(private val historyRepository: HistoryRepository) {
    private val _gameState = MutableStateFlow<GameState>(GameState.NotStarted)
    private val leftCards: ArrayList<Card> = ArrayList()
    private var initialSize = -1

    private fun updateGameState(gameState: GameState){
        _gameState.update { gameState }
    }

    fun getGameState() = _gameState.asStateFlow()

    suspend fun startGame(cardsList: List<Card>){
        updateGameState(GameState.Loading)
        delay(5000)

        if(leftCards.isEmpty()){
            leftCards.addAll(cardsList)
            initialSize = cardsList.size
        }

        val startGameState = GameState.Running(
            currentCard = leftCards.removeFirst(),
            correctAnswers = 0,
            alreadyShownCards = 0,
        )
        updateGameState(startGameState)
    }

    fun checkUserAnswer(userAnswer: UserAnswer){
        val sanitizedUserAnswer = userAnswer.answer.lowercase().trim()
        val sanitizedCorrectAnswer = userAnswer.card.polish.lowercase().trim()

        when(sanitizedUserAnswer == sanitizedCorrectAnswer){
            true -> handleCorrectAnswer(userAnswer)
            false -> handleIncorrectAnswer(userAnswer)
        }
    }


    private fun handleIncorrectAnswer(userAnswer: UserAnswer){
        val answered = GameState.Answered(
            userAnswerStr = userAnswer.answer,
            isCorrect = false,
            currentCard = userAnswer.card,
            correctAnswers = userAnswer.correctAnswers,
            alreadyShownCards = userAnswer.alreadyShownCards +1
        )
        updateGameState(answered)
    }

    private fun handleCorrectAnswer(userAnswer: UserAnswer){
        val answered = GameState.Answered(
            userAnswerStr = userAnswer.answer,
            isCorrect = true,
            currentCard = userAnswer.card,
            correctAnswers = userAnswer.correctAnswers +1,
            alreadyShownCards = userAnswer.alreadyShownCards +1
        )
        updateGameState(answered)
    }

    suspend fun nextQuestion(correctAnswers: Int, alreadyShownCards: Int){
        if (leftCards.isEmpty()){
            val finishedState = GameState.Finished(correctAnswers,alreadyShownCards)
            updateGameState(finishedState)


            val gameHistoryDto = GameHistoryDto(points = correctAnswers, maxPoints = alreadyShownCards, timestamp = System.currentTimeMillis())

            historyRepository.insertGameHistory(gameHistoryDto)
            return
        }

        val nextQuestion = GameState.Running(currentCard = leftCards.removeFirst(), correctAnswers = correctAnswers, alreadyShownCards = alreadyShownCards)
        updateGameState(nextQuestion)
    }
}