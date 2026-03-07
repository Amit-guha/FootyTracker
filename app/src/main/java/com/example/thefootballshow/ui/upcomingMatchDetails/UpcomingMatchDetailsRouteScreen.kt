package com.example.thefootballshow.ui.upcomingMatchDetails

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.thefootballshow.data.model.AwayTeam
import com.example.thefootballshow.data.model.Competition
import com.example.thefootballshow.data.model.HomeTeam
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.ui.base.ShowLoading
import com.example.thefootballshow.ui.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingMatchDetailRouteScreen(
    modifier: Modifier = Modifier,
    competitionId: Int,
    homeTeamId: Int,
    awayTeamId: Int,
    matchDetailViewModel: UpcomingMatchDetailsViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    matchDetailViewModel.apply {
        updateCompetitionId(competitionId)
        updateHomeTeamId(homeTeamId)
        updateAwayTeamId(awayTeamId)
    }

    CenterAlignedTopAppBarExample(
        homeTeam = "Arsenal",
        awayTeam = "ManCity",
        matchDetailViewModel
    ) {
        onClick()
    }
}
@Composable
@Preview(
    name = "Light Mode",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
fun DisplayMatchDetailsPreview() {
    val mockMatchInfo = MatchInfo(
        id = 1,
        competition = Competition(
            name = "Premier League", emblem = "",
        ),
        homeTeam = HomeTeam(id = 1, name = "Arsenal"),
        awayTeam = AwayTeam(id = 2, name = "Manchester City"),
        utcDate = "2024-01-15T15:00:00Z",
        status = "SCHEDULED",
        matchday = 20,
        venue = "Emirates Stadium"
    )
    DisplayMatchDetails(matchUiState = UiState.Success(mockMatchInfo))
}


@Composable
fun DisplayMatchDetails(matchUiState: UiState<MatchInfo>) {
    when (matchUiState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            val leagueName = matchUiState.data.competition?.name ?: ""
            val leagueUrl = matchUiState.data.competition?.emblem ?: ""
            val stadiumName = matchUiState.data.venue?.takeIf { it.isNotEmpty() } ?: ""
            val currentMatchDay = matchUiState.data.matchday?.toString() ?: ""

            LeagueHeader(
                leagueName = leagueName,
                url = leagueUrl,
                stadiumName = stadiumName,
                currentMatchDay = currentMatchDay
            )

            matchUiState.data.run {
                CompetitionBetweenTeamsTimeInfo(matchUiState.data)
            }

        }
    }

}


@Composable
@Preview
private fun ShowUpcomingMatchDetailRouteScreen() {
    UpcomingMatchDetailRouteScreen(competitionId = 1, homeTeamId = 1, awayTeamId = 1) {

    }
}
