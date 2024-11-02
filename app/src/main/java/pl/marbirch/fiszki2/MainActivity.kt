package pl.marbirch.fiszki2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.marbirch.fiszki2.data.model.domain.GameEvent
import pl.marbirch.fiszki2.data.model.domain.MenuEvents
import pl.marbirch.fiszki2.ui.routes.Routes
import pl.marbirch.fiszki2.ui.screens.GameScreen
import pl.marbirch.fiszki2.ui.screens.HistoryRow
import pl.marbirch.fiszki2.ui.screens.HistoryScreen
import pl.marbirch.fiszki2.ui.screens.StartScreen
import pl.marbirch.fiszki2.ui.theme.defaultStartScreenColor
import pl.revolshen.fiszki.ui.theme.Fiszki2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainVm = viewModel(modelClass = MainViewModel::class.java)
            MainApp(viewModel = mainVm)
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel) {
    Fiszki2Theme {
        val navController = rememberNavController()
        NavigationHost(vm = viewModel, navHostController = navController)
    }
}

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    vm: MainViewModel,
    navHostController: NavHostController
) {
    Surface(modifier = modifier.fillMaxSize(), color = defaultStartScreenColor) {
        NavHost(
            modifier = modifier,
            navController = navHostController,
            startDestination = Routes.startScreen
        ) {
            composable(Routes.startScreen) {
                StartScreen(onNewGame = {
                    vm.handleMenuEvent(MenuEvents.NewGame)
                    navHostController.navigate(Routes.gameScreen)
                },
                    onHistory = {
                        navHostController.navigate(Routes.historyScreen)
                    })
            }
            composable(Routes.gameScreen) {
                val state by vm.gameState.collectAsState()
                GameScreen(
                    gameState = state,
                    onChecked = { userAnswer ->
                        val event = GameEvent.Check(userAnswer)
                        vm.handleGameEvents(event)
                    },
                    onNext = { userAnswer ->
                        val event = GameEvent.Next(
                            correctAnswers = userAnswer.correctAnswers,
                            alreadyShownCards = userAnswer.alreadyShownCards
                        )
                        vm.handleGameEvents(event)
                    },
                    onFinished = {
                        navHostController.navigate(Routes.startScreen) {
                            popUpTo(navHostController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Routes.historyScreen) {
                val historyState by vm.historyState.collectAsState()
                HistoryScreen(historyList = historyState)
            }
        }
    }

}
