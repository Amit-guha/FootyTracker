package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.AwayTeam
import com.example.thefootballshow.data.model.Competition
import com.example.thefootballshow.data.model.HomeTeam
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.Standings
import com.example.thefootballshow.ui.base.ShowLoading
import com.example.thefootballshow.ui.base.TopAppBar
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.ui.leagueTable.TeamStandingInLeague
import com.example.thefootballshow.utils.enumUtills.FixturesEnum
import com.example.thefootballshow.utils.extension.getResultColor
import com.example.thefootballshow.utils.extension.loadAsyncImage
import com.example.thefootballshow.utils.extension.showLog
import com.example.thefootballshow.utils.extension.toLocalDateAndMonth
import com.example.thefootballshow.utils.extension.toLocalTime


//{{url}}/v4/matches/497520 -- match details before game
//{{url}}/v4/teams/57/matches?status=FINISHED&season=2024&competitions&limit=5  -- last 5 match details
//{{url}}/v4/competitions/PL/standings?season=2024  -- premier league table
//{{url}}/v4/matches/497526/head2head?limit=5 -- head to head matches


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarExample(
    homeTeam: String,
    awayTeam: String,
    viewModel: UpcomingMatchDetailsViewModel,
    onClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val matchUiState: UiState<MatchInfo> by viewModel.preMatchDetailsInfo.collectAsStateWithLifecycle()

    val homeTeamUiState: UiState<List<MatchInfo>> by viewModel.homeTeamMatchData.collectAsStateWithLifecycle()
    val awayTeamUiState: UiState<List<MatchInfo>> by viewModel.awayTeamMatchData.collectAsStateWithLifecycle()
    val leagueTableUiState: UiState<Standings> by viewModel.leagueTableInfo.collectAsStateWithLifecycle()

    viewModel.getPreMatchDetailsInfo()
    viewModel.getLastFiveMatchDetails()
    viewModel.getStandingInfo()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = getMatchTitleText(matchUiState),
                scrollBehavior = scrollBehavior
            ) {}
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            DisplayMatchDetails(matchUiState)
            LeagueHeadLine(text = stringResource(R.string.lastFiveGames))
            CompetitionInfo(
                onAllCallback = {},
                onHomeCallback = {},
                onAwayCallback = {}
            )
            TeamStatLazyColumn(homeTeamUiState = homeTeamUiState, awayTeamUiState = awayTeamUiState)
            LeagueHeadLine(text = stringResource(R.string.league_table))
            LeagueTableSeasonSpinner()
            LeagueTable(leagueTableUiState)
        }

    }
}

fun getMatchTitleText(matchUiState: UiState<MatchInfo>): String {
    return when (matchUiState) {
        is UiState.Success -> {
            val matchInfo = matchUiState.data
            "${matchInfo.homeTeam?.shortName} vs ${matchInfo.awayTeam?.shortName}"
        }

        is UiState.Loading -> ""
        is UiState.Error -> ""
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LeagueTable(
    tableInfoUiState: UiState<Standings>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    when (tableInfoUiState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            val data = tableInfoUiState.data
            context.showLog(tag = "Standings", message = "${data.standings.size}")
            if (data.standings.isNotEmpty() && data.standings[0].table.isNotEmpty()) {
                LazyColumn {
                    stickyHeader {
                        TeamStandingInLeague(table = data.standings[0].table[0])
                    }
                    itemsIndexed(data.standings[0].table) { index, item ->
                        if (index != 0) {
                            TeamStandingInLeague(item)
                        }
                    }
                }
            }

        }
    }


}


@Composable
fun TeamStatLazyColumn(
    homeTeamUiState: UiState<List<MatchInfo>>,
    awayTeamUiState: UiState<List<MatchInfo>>
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        HomeTeamStats(homeTeamUiState = homeTeamUiState)
        AwayTeamStats(awayTeamUiState = awayTeamUiState)
    }
}


@Composable
fun RowScope.HomeTeamStats(
    homeTeamUiState: UiState<List<MatchInfo>>,
    modifier: Modifier = Modifier
) {
    when (homeTeamUiState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            Box(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                LazyColumn(modifier = modifier.padding(top = 20.dp)) {
                    items(homeTeamUiState.data) { item ->
                        HomeTeamStat(
                            matchInfo = item
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun RowScope.AwayTeamStats(
    awayTeamUiState: UiState<List<MatchInfo>>,
    modifier: Modifier = Modifier
) {
    when (awayTeamUiState) {
        is UiState.Error -> {}
        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            Box(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                LazyColumn(modifier = modifier.padding(top = 20.dp)) {
                    items(awayTeamUiState.data) { item ->
                        AwayTeamStat(matchInfo = item)
                    }
                }
            }
        }
    }
}


@Composable
fun HomeTeamStat(
    modifier: Modifier = Modifier,
    matchInfo: MatchInfo
) {
    Row(
        modifier = modifier
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HomeTeamResultInfo(0.4f, matchInfo)
        matchInfo.score?.getResultColor()?.let {
            TeamVsTeamDrawingRect(0.1f, it)
        }


    }

}


@Composable
fun AwayTeamStat(
    modifier: Modifier = Modifier,
    matchInfo: MatchInfo
) {
    Row(
        modifier = modifier
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val color = matchInfo.score?.getResultColor()
        color?.let {
            AwayTeamDrawingRect(0.1f, color)
        }
        AwayTeamResultInfo(0.4f, matchInfo)
    }
}

@Composable
fun RowScope.AwayTeamDrawingRect(weight: Float, color: Color) {
    Row(
        Modifier.weight(weight),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            Modifier
                .size(10.dp, 10.dp)
                .align(Alignment.Bottom)
                .background(color = color, shape = RectangleShape)
        )
    }

}

@Composable
fun RowScope.TeamVsTeamDrawingRect(weight: Float, color: Color) {
    Row(
        Modifier.weight(weight),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            Modifier
                .size(10.dp, 10.dp)
                .align(Alignment.CenterVertically)
                .background(color = color, shape = RectangleShape)
        )

        /* Box(
             Modifier
                 .padding(start = 5.dp)
                 .size(15.dp, 15.dp)
                 .background(color = Color.DarkGray, shape = RectangleShape)
         )*/
    }

}

@Composable
fun RowScope.HomeTeamResultInfo(
    weight: Float,
    matchInfo: MatchInfo
) {
    Row(
        Modifier
            .fillMaxWidth()
            .weight(weight),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            matchInfo.homeTeam?.tla?:"",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                // color = Color.White,
                fontWeight = Medium,
                textAlign = TextAlign.Start
            )
        )

        Text(
            "${matchInfo.score?.fullTime?.home} - ${matchInfo.score?.fullTime?.away}",
            Modifier.padding(start = 5.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = Medium,
                textAlign = TextAlign.Center
            )
        )

        Text(
            matchInfo.awayTeam?.tla ?: "",
            Modifier.padding(start = 5.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = Medium,
                textAlign = TextAlign.Start
            )
        )
    }
}


@Composable
fun RowScope.AwayTeamResultInfo(
    weight: Float,
    matchInfo: MatchInfo
) {
    Row(
        Modifier
            .fillMaxWidth()
            .weight(weight),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            matchInfo.homeTeam?.tla ?: "",
            style = TextStyle(
                fontSize = 18.sp,
                // color = Color.White,
                color = Color.Black,
                fontWeight = Medium,
                textAlign = TextAlign.Start
            )
        )

        Text(
            "${matchInfo.score?.fullTime?.home} - ${matchInfo.score?.fullTime?.away}",
            Modifier.padding(start = 5.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = Medium,
                textAlign = TextAlign.End
            )
        )

        Text(
            matchInfo.awayTeam?.tla ?: "",
            Modifier.padding(start = 5.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = Medium,
                textAlign = TextAlign.End
            )
        )
    }
}

@Composable
fun LeagueHeadLine(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(start = 20.dp, top = 20.dp),
        text = text,
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    )
}

@Composable
fun CompetitionInfo(
    selectedItem: FixturesEnum = FixturesEnum.ALL,
    onAllCallback: () -> Unit,
    onHomeCallback: () -> Unit,
    onAwayCallback: () -> Unit
) {
    Row(
        Modifier.padding(start = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(0.5f)) {
            Text(
                stringResource(R.string.all),
                modifier = Modifier.weight(0.1f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedItem == FixturesEnum.ALL) colorResource(id = R.color.moonstone) else Color.DarkGray
                )
            )
            Text(
                stringResource(R.string.home),
                modifier = Modifier
                    .weight(0.1f)
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedItem == FixturesEnum.HOME) colorResource(id = R.color.moonstone) else Color.White
                )
            )
            Text(
                stringResource(R.string.away),
                modifier = Modifier.weight(0.1f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedItem == FixturesEnum.AWAY) colorResource(id = R.color.moonstone) else Color.White
                )
            )
        }


    }
}

@Composable
fun LeagueTableSeasonSpinner(modifier: Modifier = Modifier) {
    val listOfItems = listOf(
        "SEASON 2024/25",
        "SEASON 2023/24",
        "SEASON 2022/23",
        "SEASON 2021/22",
        "SEASON 2019/20"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(listOfItems[0]) }

    Row(
        modifier = Modifier
            .padding(start = 20.dp)
            .clickable {
                expanded = !expanded
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = selectedItem)
        Image(
            painter = painterResource(R.drawable.arrow_drop_down),
            colorFilter = ColorFilter.tint(color = Color.Black),
            contentDescription = "Spinner"
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            listOfItems.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        expanded = false
                        selectedItem = it
                    }
                )
            }

        }

    }

}

@Composable
fun Fixtures(modifier: Modifier = Modifier) {
    Row {
        Text(
            stringResource(R.string.all),
            modifier = modifier.weight(0.1f)
        )
        Text(
            stringResource(R.string.home),
            modifier = modifier.weight(0.1f)
        )
        Text(
            stringResource(R.string.away),
            modifier = modifier.weight(0.1f)
        )

    }
}

@Composable
fun CompetitionBetweenTeamsTimeInfo(data: MatchInfo) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HomeTeamVsAwayTeamLogo(modifier = Modifier.weight(1f), data = data)
        TeamVsTeamLastWinningInfo(modifier = Modifier.weight(1f), data = data)
        MatchStartTimeInfo(modifier = Modifier.weight(1f), data = data)

    }
}

@Preview(showBackground = true)
@Composable
fun CompetitionBetweenTeamsTimeInfoPreview() {
    CompetitionBetweenTeamsTimeInfo(
        data = MatchInfo(
            id = 12345,
            homeTeam = HomeTeam(
                id = 1,
                name = "Chelsea",
                shortName = "Chelsea",
                tla = "CHE",
                crest = ""
            ),
            awayTeam = AwayTeam(
                id = 2,
                name = "Arsenal",
                shortName = "Arsenal",
                tla = "ARS",
                crest = ""
            ),
            utcDate = "2024-03-15T15:00:00Z",
            competition = Competition(
                id = 2021,
                name = "Premier League",
                code = "PL",
                type = "LEAGUE"
            ),
            status = "SCHEDULED",
            matchday = 28
        )
    )
}


@Composable
fun MatchStartTimeInfo(modifier: Modifier = Modifier, data: MatchInfo) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd,
    ) {
        Column(
            modifier = modifier
        ) {
            Text(text = data.utcDate.takeIf { it?.isNotEmpty() == true}?.toLocalDateAndMonth() ?: "")
            Text(
                text = data.utcDate.takeIf { it?.isNotEmpty() == true }?.toLocalTime() ?: "",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

    }
}

@Composable
fun TeamVsTeamLastWinningInfo(modifier: Modifier, data: MatchInfo) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ){
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeTeamVsAwayTeamWinningStatistics(
                nameOfTheTeam = data.homeTeam?.tla ?: "",
                winCount = "2"
            )
            Spacer(modifier = Modifier.height(2.dp))
            HomeTeamVsAwayTeamWinningStatistics(
                nameOfTheTeam = data.awayTeam?.tla ?: "",
                winCount = "10"
            )
        }
    }


}


@Composable
 fun HomeTeamVsAwayTeamLogo(modifier: Modifier, data: MatchInfo) {
    val context = LocalContext.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center) {
        data.awayTeam?.crest.takeIf { it?.isNotEmpty() == true }?.let {
            Modifier
                .size(50.dp)
                .clip(CircleShape)
                .loadAsyncImage(
                    url = it,
                    context = context,
                    contentDescription = stringResource(R.string.away_team_logo)
                )()
        } ?: Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.pl_main_logo),
            contentDescription = stringResource(R.string.away_team_logo)
        )

        data.homeTeam?.crest.takeIf { it?.isNotEmpty() == true }?.let {
            Box {
                Modifier
                    .size(50.dp)
                    .offset(x = (-25).dp)
                    .clip(CircleShape)
                    .loadAsyncImage(
                        url = it,
                        context = context,
                        contentDescription = stringResource(R.string.home_team_logo)
                    )()
            }
        } ?: Box {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = (-25).dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.pl_main_logo),
                contentDescription = stringResource(R.string.home_team_logo)
            )
        }


    }
}


@Composable
fun HomeTeamVsAwayTeamWinningStatistics(
    modifier: Modifier = Modifier,
    nameOfTheTeam: String,
    winCount: String
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(
            modifier = modifier.padding(start = 10.dp),
            text = nameOfTheTeam,
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Box(
            modifier = modifier
                .padding(start = 10.dp)
                .size(25.dp)
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = winCount,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = Medium
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeTeamVsAwayTeamWinningStatisticsPreview() {
    HomeTeamVsAwayTeamWinningStatistics(
        nameOfTheTeam = "Barcelona",
        winCount = "5"
    )
}



@Composable
fun LeagueHeader(
    leagueName: String,
    url: String,
    stadiumName: String,
    currentMatchDay: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = leagueName.ifEmpty { "" },
                style = TextStyle(
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = Medium
                )
            )

            if (url.isNotEmpty()) {
                Spacer(modifier = Modifier.width(10.dp))
                Modifier
                    .padding(start = 10.dp)
                    .width(44.dp)
                    .height(44.dp)
                    .clip(CircleShape)
                    .loadAsyncImage(
                        url = url,
                        context = context,
                        contentDescription = "League Logo"
                    )()
            }
        }

        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*if (stadiumName.isNotEmpty() && currentMatchDay.isNotEmpty()) {
                Text(
                    text = stadiumName,
                    style = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal
                    ),
                )

                if (currentMatchDay.isNotEmpty()) {
                    Text(
                        text = " | ",
                        fontSize = 25.sp,
                    )
                }
            }*/
            Text(
                text = "MatchDay $currentMatchDay",
                style = TextStyle(
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                ),
            )

        }
    }
}

@Composable
@Preview(name = "LeagueHeader - Preview", showBackground = true)
fun Preview_LeagueHeader() {
    LeagueHeader(
        leagueName = "Premier League",
        url = "",
        stadiumName = "Emirates Stadium",
        currentMatchDay = "20"
    )
}
