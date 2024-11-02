package pl.marbirch.fiszki2.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.marbirch.fiszki2.data.model.dto.GameHistoryDto

@Dao
interface GameHistoryDao {

    @Query("SELECT * FROM game_history")
    fun getAllHistoryGames(): Flow<List<GameHistoryDto>>

    @Insert
    fun insert(gameHistoryDto: GameHistoryDto)

    @Delete
    fun delete(gameHistoryDto: GameHistoryDto)
}