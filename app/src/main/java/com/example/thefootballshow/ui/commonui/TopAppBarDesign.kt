package com.example.thefootballshow.ui.commonui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.ui.theme.Oswald

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUi(
    title: String,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = Oswald,
                    fontSize = 18.sp,
                ),
            )
        },
        actions = {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = Oswald,
                    fontSize = 18.sp,
                ),
                textAlign = TextAlign.Center
            )
        }

    )
}

@Preview
@Composable
fun TopAppBarUiPreview() {
    TopAppBarUi(title = "Football")
}