package com.example.thefootballshow.ui.laligascreenroute

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thefootballshow.ui.base.SetLeagueTitleText
import com.example.thefootballshow.ui.base.UpcomingMatchesText
import com.example.thefootballshow.ui.navigation.BottomBarScreen

@Composable
fun LaligaScreenRoute(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        SetLeagueTitleText(leagueTitle = BottomBarScreen.LaligaScreen.title)
        Spacer(modifier = modifier.height(40.dp))
        UpcomingMatchesText()
    }

}

@Preview(showSystemUi = true)
@Composable
private fun ShowLaligaScreenRoute() {
    LaligaScreenRoute()
}