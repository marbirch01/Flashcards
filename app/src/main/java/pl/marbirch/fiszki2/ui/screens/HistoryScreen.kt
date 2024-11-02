package pl.marbirch.fiszki2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.marbirch.fiszki2.data.model.domain.GameHistory
import pl.marbirch.fiszki2.ui.theme.defaultHistoryCardColor
import pl.marbirch.fiszki2.ui.theme.defaultHistoryTextColor
import pl.marbirch.fiszki2.ui.theme.defaultTextColor
import pl.revolshen.fiszki.ui.theme.Typography

@Composable
fun HistoryScreen(historyList: List<GameHistory>, modifier: Modifier = Modifier) {

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .fillMaxSize()
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = "History",
                textAlign = TextAlign.Center,
                style = Typography.titleLarge,
                color = defaultTextColor
            )
            LazyColumn(modifier= Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(vertical = 16.dp)) {
                items(historyList) {
                    HistoryRow(it)
                }
            }
        }
    }
}

@Composable
fun HistoryRow(gameHistory: GameHistory, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = defaultHistoryCardColor)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = gameHistory.date, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = defaultHistoryTextColor)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "${gameHistory.points}/${gameHistory.maxPoints}",fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = defaultHistoryTextColor )
        }
    }
}

@Preview
@Composable
private fun HistoryRowPreview() {
    HistoryRow(gameHistory = GameHistory(points = 19, maxPoints = 20, date = "20.12.2021"))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistoryScreenPreview() {
    val list = buildList {
        for (i in 0..100){
            val history = GameHistory(points = i%20, maxPoints = 20, date = "20.12.2023")
            add(history)
        }
    }
    HistoryScreen(historyList = list)
}