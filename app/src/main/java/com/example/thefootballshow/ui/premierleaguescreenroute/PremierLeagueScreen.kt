package com.example.thefootballshow.ui.premierleaguescreenroute

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.thefootballshow.data.model.Matche
import com.example.thefootballshow.ui.base.SeeAllText
import com.example.thefootballshow.ui.base.SetLeagueTitleText
import com.example.thefootballshow.ui.base.ShowLoading
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.ui.base.UpcomingMatchesText
import com.example.thefootballshow.utils.extension.toAmPmFormat
import com.example.thefootballshow.utils.extension.toFriendlyDate

@Composable
fun PremierLeagueScreenRoute(
    modifier: Modifier = Modifier,
    premierLeagueViewModel: PremierLeagueViewModel = hiltViewModel(),
    onItemClick: () -> Unit
) {

    val matchUiState: UiState<List<Matche>> by premierLeagueViewModel.matchUiState.collectAsStateWithLifecycle()
    premierLeagueViewModel.getUpcomingMatches()

    Log.d(
        "PremierLeagueScreenRoute",
        "PremierLeagueScreenRoute: ${"2023-02-05T20:00:00Z".toFriendlyDate()}"
    )

    Column(modifier = modifier.fillMaxSize()) {
        SetLeagueTitleText(leagueTitle = "Premier League")
        Spacer(modifier = modifier.height(40.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            UpcomingMatchesText()
            SeeAllText {}
        }
        Spacer(modifier = modifier.height(5.dp))
        UpcomingMatchListScreen(matchUiState){
            onItemClick()
        }

    }

}


@Composable
fun UpcomingMatchListScreen(matchUiState: UiState<List<Matche>>,onItemClick: () -> Unit) {
    when (matchUiState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            UpcomingMatchList(matchUiState.data){
                onItemClick()
            }
        }

        else -> {}
    }
}


@Composable
fun UpcomingMatchList(data: List<Matche>,onItemClick:() -> Unit) {
    LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
        items(data) {
            FullCard(data = it){
                onItemClick()
            }
        }
    }

}


//https://dribbble.com/shots/22396996-Balbalan-Live-Score-Football-App

@Composable
fun FullCard(modifier: Modifier = Modifier, data: Matche,onClick :() ->Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
            .clickable {
                onClick()
                Log.d("FullCard", "FullCard: ${data.utcDate}")
            }, shape = RoundedCornerShape(10.dp),

        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
        ) {
            HomeTeamCard(data = data)
            MatchTimeCard(data = data)
            AwayTeamCard(data = data)
        }

    }
}


@Composable
fun RowScope.HomeTeamCard(modifier: Modifier = Modifier, data: Matche) {
    Box(
        modifier = modifier.weight(1f),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardImage(url = data.homeTeam.crest)
            ClubTextName(teamName = data.homeTeam.shortName)
        }
    }
}

@Composable
fun RowScope.AwayTeamCard(modifier: Modifier = Modifier, data: Matche) {
    Box(
        modifier = modifier.weight(1f),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = modifier
                .padding(end = 15.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardImage(url = data.awayTeam.crest)
            ClubTextName(teamName = data.awayTeam.shortName)
        }
    }
}

@Composable
fun RowScope.MatchTimeCard(modifier: Modifier = Modifier, data: Matche) {
    Box(
        modifier = modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DayTextName(dayDescription = data.utcDate.toFriendlyDate())
            Spacer(modifier = modifier.height(2.dp))
            TimeTextName(time = data.utcDate.toAmPmFormat())
        }
    }
}


@Composable
fun CardImage(modifier: Modifier = Modifier, url: String) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        shape = CircleShape
    ) {
        AsyncImage(
            model = if (url.contains("svg")) {
                ImageRequest.Builder(context)
                    .data(url)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            } else {
                url
            },
            modifier = modifier
                .height(44.dp)
                .width(44.dp)
                .background(color = Color.LightGray)
                .padding(6.dp),
            contentDescription = "Team Description"
        )
    }
}

@Composable
fun ClubTextName(modifier: Modifier = Modifier, teamName: String) {
    Text(
        text = teamName,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 14.sp,
        modifier = modifier.padding(top = 8.dp)
    )
}

@Composable
fun DayTextName(modifier: Modifier = Modifier, dayDescription: String) {
    Text(
        text = dayDescription.ifEmpty { "" },
        modifier = modifier,
        textAlign = TextAlign.Justify,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 15.sp,
    )
}

@Composable
fun TimeTextName(modifier: Modifier = Modifier, time: String) {
    Text(
        text = time.ifEmpty { "" },
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displayLarge
    )
}


@Preview(showSystemUi = true)
@Composable
private fun ShowLaligaScreenRoute() {
    PremierLeagueScreenRoute {

    }
}

//{{url}}/v4/teams/81/matches?status=FINISHED&season=2023 -- last 5 matches
//{{url}}/v4/matches/438848/head2head?limit=5  -- last five head to head
//win, loss probability for each team
//{{url}}/v4/matches/438848 --match info