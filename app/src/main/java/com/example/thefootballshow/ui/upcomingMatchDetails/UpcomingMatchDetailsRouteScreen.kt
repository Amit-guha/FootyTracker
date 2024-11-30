package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UpcomingMatchDetailRouteScreen(
    modifier: Modifier = Modifier,
    competitionId: Int,
    homeTeamId: Int,
    awayTeamId: Int,
    matchDetailViewModel : UpcomingMatchDetailsViewModel = hiltViewModel()
) {

    CenterAlignedTopAppBarExample("Chelsea", "Arsenal") {

    }
}

@Composable
@Preview
private fun ShowUpcomingMatchDetailRouteScreen() {
    UpcomingMatchDetailRouteScreen(competitionId = 1, homeTeamId = 1, awayTeamId = 1)


    }
