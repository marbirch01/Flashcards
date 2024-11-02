package pl.marbirch.fiszki2.data.model.domain

import pl.marbirch.fiszki2.data.model.dto.GameHistoryDto
import java.text.SimpleDateFormat
import java.util.Locale

data class GameHistory(val id: Int =0, val points: Int = 0, val maxPoints: Int = 0, val date: String = ""){

    fun toDto(): GameHistoryDto {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val convertedDate = sdf.parse(date)?.time ?: 0L

        return GameHistoryDto(id = id, points = points, maxPoints = maxPoints, timestamp = convertedDate)
    }
}
