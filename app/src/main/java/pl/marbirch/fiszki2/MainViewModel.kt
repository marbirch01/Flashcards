package pl.marbirch.fiszki2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.marbirch.fiszki2.data.model.Card
import pl.marbirch.fiszki2.data.model.domain.GameEvent
import pl.marbirch.fiszki2.data.model.domain.GameState
import pl.marbirch.fiszki2.data.model.domain.MenuEvents
import pl.marbirch.fiszki2.data.repositories.CardRepository
import pl.marbirch.fiszki2.data.repositories.GameRepository
import pl.marbirch.fiszki2.data.repositories.HistoryRepository

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val historyRepository = HistoryRepository(app)
    private val gameRepository = GameRepository(historyRepository)
    private val cardRepository = CardRepository(app)

    val gameState = gameRepository.getGameState()
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = GameState.NotStarted)

    val historyState = historyRepository.getAllHistoryGames().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())

    fun handleMenuEvent(menuEvents: MenuEvents) = viewModelScope.launch {
        when(menuEvents){
            MenuEvents.NewGame -> {
                val success = loadCards()
                if (success){
                    gameRepository.startGame(getCards())
                }
            }
            MenuEvents.History -> {}
        }
    }

    fun handleGameEvents(gameEvent: GameEvent) = viewModelScope.launch {
        when(gameEvent){
            is GameEvent.Check -> {
                gameRepository.checkUserAnswer(userAnswer = gameEvent.userAnswer)
            }
            is GameEvent.Next -> {
                gameRepository.nextQuestion(correctAnswers = gameEvent.correctAnswers, alreadyShownCards = gameEvent.alreadyShownCards)
            }
        }
    }

    private fun loadCards(): Boolean{
        return cardRepository.loadCards()
    }

    private fun getCards(): List<Card>{
        return cardRepository.getCards()
    }

}