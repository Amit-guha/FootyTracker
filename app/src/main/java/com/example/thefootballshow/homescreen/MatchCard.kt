package com.example.thefootballshow.homescreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.thefootballshow.utils.extension.Dimens

@Composable
fun MatchCard(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.padding(
            start = Dimens.PaddingMedium,
            end = Dimens.PaddingMedium
        )
    ) {

    }

}

@Composable
@Preview(showBackground = true)
fun MatchCardPreview() {
    MatchCard()
}