package pl.marbirch.fiszki2.data.repositories

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.marbirch.fiszki2.data.local.database.GameHistoryDao
import pl.marbirch.fiszki2.data.local.database.GameHistoryDatabaseManager
import pl.marbirch.fiszki2.data.model.domain.GameHistory
import pl.marbirch.fiszki2.data.model.dto.GameHistoryDto

class HistoryRepository(app: Application) {
    private val manager = GameHistoryDatabaseManager
    private val db = manager.getDatabase(app)
    private val dao = db.historyDao()

    fun getAllHistoryGames(): Flow<List<GameHistory>>{
        return dao.getAllHistoryGames().map { it.convertToModel() }
    }

    suspend fun insertGameHistory(gameHistoryDto: GameHistoryDto) = withContext(Dispatchers.IO){
        dao.insert(gameHistoryDto)
    }

    private fun List<GameHistoryDto>.convertToModel(): List<GameHistory>{
        return map { it.toModel() }
    }
}