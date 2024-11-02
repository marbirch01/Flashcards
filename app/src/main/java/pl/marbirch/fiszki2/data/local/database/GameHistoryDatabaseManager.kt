package pl.marbirch.fiszki2.data.local.database

import android.app.Application
import androidx.room.Room

object GameHistoryDatabaseManager {

    private var db: GameHistoryDatabase? = null
    fun getDatabase(application: Application): GameHistoryDatabase{
        return db ?: synchronized(this){
            val tempInstance = db
            if(tempInstance != null){
                return@synchronized tempInstance
            }
            val instance = Room.databaseBuilder(context = application, klass = GameHistoryDatabase::class.java, name = "game_history_database").build()

            db = instance
            return@synchronized instance
        }
    }
}