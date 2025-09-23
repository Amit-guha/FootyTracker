package com.example.thefootballshow.ui.commonui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.ui.theme.Oswald

@Composable
fun MatchTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        style = TextStyle(
            fontFamily = Oswald,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

    )

}

@Composable
@Preview(showBackground = true)
fun MatchTitlePreview() {
    MatchTitle(title = "Live Matches")
}