package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
    matchDetailViewModel : UpcomingMatchDetailsViewModel = hiltViewModel()
) {
    matchDetailViewModel.apply {
        updateCompetitionId(competitionId)
        updateHomeTeamId(homeTeamId)
        updateAwayTeamId(awayTeamId)
    }

    CenterAlignedTopAppBarExample(homeTeam = "Arsenal", awayTeam ="ManCity", matchDetailViewModel) {

    }
}


@Composable
fun DisplayMatchDetails(matchUiState: UiState<MatchInfo>) {
    when(matchUiState){
        is UiState.Error ->{}
        UiState.Loading -> {
            ShowLoading()
        }
        is UiState.Success -> {
            LeagueTitle(leagueName =  matchUiState.data.competition.name, url = matchUiState.data.competition.emblem)
            matchUiState.data.run {
                val stadiumName = venue?.takeIf { it.isNotEmpty() } ?: ""
                val currentMatchDay = matchday.toString() ?: ""

                StadiumAndGameWeekDetails(
                    stadiumName = stadiumName,
                    currentMatchDay = currentMatchDay
                )

                CompetitionBetweenTeamsTimeInfo(matchUiState.data)
            }

        }
    }

}


@Composable
@Preview
private fun ShowUpcomingMatchDetailRouteScreen() {
    UpcomingMatchDetailRouteScreen(competitionId = 1, homeTeamId = 1, awayTeamId = 1)


    }
