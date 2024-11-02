package pl.marbirch.fiszki2.ui.default_component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.marbirch.fiszki2.ui.theme.defaultButtonColor

@Composable
fun GameButton(text: String, modifier: Modifier = Modifier, textStyle: TextStyle = TextStyle(), onClick: () -> Unit = {}) {
        ElevatedButton(
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = defaultButtonColor),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp)
        ) {
            Text(text = text, style = textStyle, color = Color.White)
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameButtonPreview() {
        GameButton(text = "Check")
}