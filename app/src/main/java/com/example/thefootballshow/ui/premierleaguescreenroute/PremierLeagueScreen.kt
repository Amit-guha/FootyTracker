package com.example.thefootballshow.ui.premierleaguescreenroute
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thefootballshow.data.model.Competitions
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.TopScorer
import com.example.thefootballshow.ui.base.SeeAllText
import com.example.thefootballshow.ui.base.ShowLoading
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.ui.base.UpcomingMatchesText
import com.example.thefootballshow.ui.leagueSelector.LeagueSelectorItem
import com.example.thefootballshow.ui.upcomingMatchDetails.MatchCard
import com.example.thefootballshow.utils.MatchNavigationParams
import com.example.thefootballshow.utils.enumUtills.MatchTypeEnum
import com.example.thefootballshow.utils.extension.showLog
import com.example.thefootballshow.utils.extension.toFriendlyDate

@Composable
fun PremierLeagueScreenRoute(
    modifier: Modifier = Modifier,
    premierLeagueViewModel: PremierLeagueViewModel = hiltViewModel(),
    onItemClick: (MatchNavigationParams) -> Unit
) {

    val matchUiState: UiState<List<MatchInfo>> by premierLeagueViewModel.matchUiState.collectAsStateWithLifecycle()
    val competitionList by premierLeagueViewModel.competitionList.collectAsStateWithLifecycle()
    val topScorerList by premierLeagueViewModel.topScorerList.collectAsStateWithLifecycle()

    /*    LaunchedEffect(Unit) {
            premierLeagueViewModel.getUpcomingMatches()
            premierLeagueViewModel.getAllCompetitionInfo()
        }*/

    Log.d(
        "PremierLeagueScreenRoute",
        "PremierLeagueScreenRoute: ${"2023-02-05T20:00:00Z".toFriendlyDate()}"
    )
    Log.d("PremierLeagueViewModel", "ViewModel instance: $premierLeagueViewModel")


    Column(modifier = modifier.fillMaxSize()) {
        // SetLeagueTitleText(leagueTitle = "Premier League")
        Spacer(modifier = modifier.height(40.dp))
        DisplayLeagueSelection(competitionList) { leagueId ->
            showLog(message = leagueId.toString())
            premierLeagueViewModel.updateAreaCompetition(leagueId)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            UpcomingMatchesText()
            SeeAllText {
                onItemClick(
                    MatchNavigationParams(
                        null,
                        null,
                        null,
                        MatchTypeEnum.ALL_MATCH_ENUM,
                        premierLeagueViewModel.getLeagueId()
                    )
                )
            }
        }
        Spacer(modifier = modifier.height(5.dp))
        UpcomingMatchListScreen(
            matchUiState,
            onItemClick = { competitionId, homeTeamId, awayTeamId ->
                onItemClick(
                    MatchNavigationParams(
                        competitionId, homeTeamId, awayTeamId,
                        MatchTypeEnum.MATCH_DETAILS_ENUM
                    )
                )
            })
        Spacer(modifier = Modifier.height(5.dp))

        TopScoreUI()
        DisplayTopScorer(topScorerList)

    }

}

@Composable
fun DisplayTopScorer(topScorerList: UiState<TopScorer>) {
    when (topScorerList) {
        is UiState.Error -> {}

        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success<*> -> {
            val topScorerData = topScorerList.data as TopScorer
            LazyColumn {
                items(topScorerData.scorers) { scorer ->
                    TopScorerItem(scorer = scorer)
                }
            }
        }
    }
}


@Composable
fun DisplayLeagueSelection(
    competitionList: UiState<Competitions>,
    onItemClick: (leagueId: Int) -> Unit
) {
    when (competitionList) {
        is UiState.Error -> {
            val context = LocalContext.current
            Toast.makeText(context, competitionList.message, Toast.LENGTH_SHORT).show()
        }

        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            val competitions = competitionList.data.competitions
            /*      val context = LocalContext.current
                  context.showLog(message = competitions.size.toString())*/
            LazyRow(modifier = Modifier.padding(start = 8.dp)) {
                items(competitions) {
                    LeagueSelectorItem(areaCompetition = it) { leagueId ->
                        onItemClick(leagueId)
                    }
                }
            }

        }
    }
}

@Composable
fun UpcomingMatchListScreen(
    matchUiState: UiState<List<MatchInfo>>,
    onItemClick: (Int, Int, Int) -> Unit
) {
    when (matchUiState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            UpcomingMatchList(
                matchUiState.data,
                onItemClick = { competitionId, homeTeamId, awayTeamId ->
                    onItemClick(competitionId, homeTeamId, awayTeamId)
                })
        }
    }
}


@Composable
private fun UpcomingMatchList(data: List<MatchInfo>, onItemClick: (Int, Int, Int) -> Unit) {
    LazyRow(modifier = Modifier.padding(bottom = 20.dp)) {
        items(data) {
            MatchCard(data = it, onClick = { competitionId, homeTeamId, awayTeamId ->
                onItemClick(competitionId, homeTeamId, awayTeamId)
            })
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ShowLaligaScreenRoute() {
    /*  PremierLeagueScreenRoute(onItemClick = { _, _, _ ->
      })*/
}

//{{url}}/v4/teams/81/matches?status=FINISHED&season=2023 -- last 5 matches
//{{url}}/v4/matches/438848/head2head?limit=5  -- last five head to head
//win, loss probability for each team
//{{url}}/v4/matches/438848 --match info
//https://gist.github.com/akeaswaran/b48b02f1c94f873c6655e7129910fc3b
//https://dribbble.com/shots/2354722-Day-019-Leaderboard