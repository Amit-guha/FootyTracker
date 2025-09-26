package com.example.thefootballshow.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.thefootballshow.utils.extension.Dimens.PaddingMedium

@Composable
fun LiveMatches() {
    LazyColumn(
        contentPadding = PaddingValues(PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(PaddingMedium)
    ) {
        items(1) { index ->
            MatchCard()
        }
    }

}

@Composable
@Preview(showBackground = true)
fun LiveMatchesPreview() {
    LiveMatches()
}