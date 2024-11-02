package pl.marbirch.fiszki2.ui.default_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.marbirch.fiszki2.data.model.domain.UserAnswer
import pl.marbirch.fiszki2.ui.theme.defaultButtonColor
import pl.marbirch.fiszki2.ui.theme.defaultTextColor
import pl.revolshen.fiszki.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = defaultTextColor,
    userAnswer: String = "",
    enableEditing: Boolean = true,
    onValueChange: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(48.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = text, style = Typography.titleLarge, color = defaultTextColor)
            TextField(
                modifier = Modifier.padding(top = 32.dp),
                value = userAnswer,
                onValueChange = onValueChange,
                enabled = enableEditing,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = defaultTextColor,
                    cursorColor = defaultTextColor
                ),
                singleLine = true,
                textStyle = Typography.titleMedium.copy(
                    textAlign = TextAlign.Center, color = textColor
                ),
                placeholder = {
                    Text(modifier = Modifier.fillMaxWidth(), text = "Your answer", textAlign = TextAlign.Center)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun GameCardPreview() {
    GameCard(text = "El gato", userAnswer = "Kot")
}