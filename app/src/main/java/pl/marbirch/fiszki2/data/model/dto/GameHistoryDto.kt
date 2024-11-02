package pl.marbirch.fiszki2.data.model.dto

import androidx.compose.ui.graphics.DefaultAlpha
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.marbirch.fiszki2.data.model.domain.GameHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max


@Entity(tableName = "game_history")
data class GameHistoryDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("points")
    val points: Int = 0,
    @ColumnInfo("max_points")
    val maxPoints: Int = 0,
    @ColumnInfo("timestamp")
    val timestamp: Long = 0
){

    fun toModel(): GameHistory {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = Date(timestamp)
        val convertedDate = sdf.format(date)

        return GameHistory(id = id, points = points, maxPoints = maxPoints, date = convertedDate)
    }
}
