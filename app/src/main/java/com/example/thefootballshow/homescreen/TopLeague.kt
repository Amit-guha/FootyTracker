package com.example.thefootballshow.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.ui.theme.Roboto

@Composable
fun TopLeagues(modifier: Modifier = Modifier) {
    LazyRow() {
        items(10) {
            RoundedLeagueItem()
        }
    }
}


@Composable
fun RoundedLeagueItem() {
    Box(
        modifier = Modifier
            .padding(end = 5.dp)
            .clip(
                shape = RoundedCornerShape(24.dp)
            )
            //F3F4F6FF
            .background(colorResource(R.color.ultramarine_blue))
            .padding(12.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
            text = "All",
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = Roboto
        )
    }
}


@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LeagueItemPreview(modifier: Modifier = Modifier) {
    TopLeagues()
}