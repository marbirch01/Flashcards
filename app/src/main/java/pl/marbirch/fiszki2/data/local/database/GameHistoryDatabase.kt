package pl.marbirch.fiszki2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.marbirch.fiszki2.data.model.dto.GameHistoryDto

@Database(entities = [GameHistoryDto::class], version = 1)
abstract class GameHistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): GameHistoryDao
}