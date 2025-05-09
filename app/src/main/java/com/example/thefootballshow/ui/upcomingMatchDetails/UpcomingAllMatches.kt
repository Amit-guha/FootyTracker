package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.ui.base.ShowLoading
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.ui.premierleaguescreenroute.FullCard
import com.example.thefootballshow.ui.premierleaguescreenroute.PremierLeagueViewModel


@Composable
fun MatchList(
    premierLeagueViewModel: PremierLeagueViewModel = hiltViewModel()
) {
    premierLeagueViewModel.getUpcomingMatches()
    val matchUiState: UiState<List<MatchInfo>> by premierLeagueViewModel.matchUiState.collectAsStateWithLifecycle()

    MatchListScreen(
        matchUiState,
        onItemClick = { competitionId, homeTeamId, awayTeamId ->
           // onItemClick(competitionId, homeTeamId, awayTeamId)
        })

}

@Composable
fun MatchListScreen(
    matchUiState: UiState<List<MatchInfo>>,
    onItemClick: (Int, Int, Int) -> Unit
) {
    when (matchUiState) {
        is UiState.Error -> {}

        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            LoadAllMatches(
                matchUiState.data,
                onItemClick = { competitionId, homeTeamId, awayTeamId ->
                    onItemClick(competitionId, homeTeamId, awayTeamId)
                })
        }

    }
}

@Composable
fun LoadAllMatches(data: List<MatchInfo>, onItemClick: (Int, Int, Int) -> Unit) {
    LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
        items(data) {
            FullCard(data = it, onClick = { competitionId, homeTeamId, awayTeamId ->
                onItemClick(competitionId, homeTeamId, awayTeamId)
            })
        }
    }
}