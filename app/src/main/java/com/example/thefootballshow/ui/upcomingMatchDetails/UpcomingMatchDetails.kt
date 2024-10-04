package com.example.thefootballshow.ui.upcomingMatchDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.utils.enumUtills.TeamStatEnum


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarExample(homeTeam: String, awayTeam: String, onClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    Text(
                        text = "$homeTeam vs $awayTeam",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )

                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LeagueTitle(leagueName = "Premier League")
            StadiumAndGameWeekDetails(stadiumName = "Emirates Stadium", gameWeek = "1")
            CompetitionBetweenTeamsTimeInfo()

            //Maintain an Enum of H2H, Table, Lineups
            TeamStatus(onClickH2H = {

            }, onClickTable = {

            }, onClickLineUps = {

            }

            )

            LastFiveGames()
            CompetitionInfo()
            // CompetitionSpinner()

        }

    }
}

@Composable
fun LastFiveGames(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 20.dp, top = 20.dp),
        text = stringResource(R.string.lastFiveGames),
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    )
}

@Composable
fun CompetitionInfo() {
    Row(
        Modifier.padding(start = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(0.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompetitionSpinner()
        }
        Row(modifier = Modifier.weight(0.5f)) {
            Text(
                stringResource(R.string.all),
                modifier = Modifier.weight(0.1f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                )
            )
            Text(
                stringResource(R.string.home),
                modifier = Modifier
                    .weight(0.1f)
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
            Text(
                stringResource(R.string.away),
                modifier = Modifier.weight(0.1f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
        }


    }
}

@Composable
fun CompetitionSpinner() {
    val listOfItems = listOf("ALL COMPETITIONS", "Premier League", "La Liga", "Serie A")
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(listOfItems[0]) }

    Row(modifier = Modifier
        .clickable {
            expanded = !expanded
        }, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = selectedItem)
        Image(painter = painterResource(R.drawable.arrow_drop_down), contentDescription = "Spinner")
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
fun LeagueTitle(leagueName: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = leagueName,
            style = MaterialTheme.typography.headlineMedium
        )

        Image(
            modifier = modifier
                .padding(start = 10.dp)
                .width(44.dp)
                .height(44.dp),
            painter = painterResource(id = R.drawable.premier),
            contentDescription = "League Logo"
        )
    }

}

@Composable
fun StadiumAndGameWeekDetails(
    stadiumName: String,
    gameWeek: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stadiumName,
            fontSize = 20.sp,
            color = Color.DarkGray,
        )

        Text(
            text = " | ",
            fontSize = 25.sp,
            color = Color.DarkGray,
        )

        Text(
            text = "GameWeek $gameWeek",
            fontSize = 20.sp,
            color = Color.DarkGray,
        )

    }

}

@Composable
fun CompetitionBetweenTeamsTimeInfo() {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(1f)
    ) {
        HomeTeamVsAwayTeamLogo()
        //Spacer(modifier = Modifier.width(15.dp))
        TeamVsTeamLastWinningInfo()
        MatchStartTimeInfo(Modifier)

    }
}


@Composable
fun TeamStatus(
    modifier: Modifier = Modifier,
    selectedEnum: TeamStatEnum = TeamStatEnum.H2H,
    onClickH2H: () -> Unit,
    onClickTable: () -> Unit,
    onClickLineUps: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier
                .weight(0.3f)
                .padding(start = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            onClickH2H()
                        },
                    text = stringResource(id = R.string.h2h),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = if (selectedEnum == TeamStatEnum.H2H) Color.Blue else Color.DarkGray,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Monospace
                    )
                )

                VerticalDivider(
                    modifier = Modifier
                        .height(45.dp),
                    thickness = 0.7.dp,
                    color = Color.DarkGray
                )
            }

            HorizontalDivider(
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                thickness = 0.5.dp,
                color = if (selectedEnum == TeamStatEnum.H2H) Color.Blue else Color.DarkGray
            )
        }


        Column(modifier.weight(0.3f)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            onClickTable()
                        },
                    text = stringResource(id = R.string.table),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = if (selectedEnum == TeamStatEnum.TABLE) Color.Blue else Color.DarkGray,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Monospace
                    )
                )

                VerticalDivider(
                    modifier = Modifier
                        .height(45.dp),
                    thickness = 0.7.dp,
                    color = Color.DarkGray
                )
            }

            HorizontalDivider(
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                thickness = 0.5.dp,
                color = if (selectedEnum == TeamStatEnum.TABLE) Color.Blue else Color.DarkGray
            )
        }


        Column(
            modifier
                .weight(0.3f)
                .padding(end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onClickLineUps()
                        },
                    text = stringResource(id = R.string.lineups),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = if (selectedEnum == TeamStatEnum.LINEUPS) Color.Blue else Color.DarkGray,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Monospace
                    )
                )

                VerticalDivider(
                    modifier = Modifier
                        .height(45.dp)
                        .alpha(0f),
                    thickness = 0.7.dp,
                    color = Color.DarkGray
                )

            }

            HorizontalDivider(
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                thickness = 0.5.dp,
                color = if (selectedEnum == TeamStatEnum.LINEUPS) Color.Blue else Color.DarkGray
            )
        }
    }
}


@Composable
fun RowScope.MatchStartTimeInfo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .weight(1f)
            .padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = modifier
        ) {
            Text(text = "13 Nov")
            Text(
                text = "16:00",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
            )
        }

    }
}

@Composable
fun RowScope.TeamVsTeamLastWinningInfo() {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTeamVsAwayTeamWinningStatistics(
            nameOfTheTeam = "MCI",
            winCount = "2"
        )
        HomeTeamVsAwayTeamWinningStatistics(
            nameOfTheTeam = "CHE",
            winCount = "10"
        )
    }

}


@Composable
private fun RowScope.HomeTeamVsAwayTeamLogo() {
    Box(
        modifier = Modifier.weight(0.5f),
        contentAlignment = Alignment.CenterStart,

        ) {
        Image(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .offset(x = 25.dp, y = 0.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.pl_main_logo),
            contentDescription = "Home Team Logo"
        )
        Box {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.pl_main_logo),
                contentDescription = "Home Team Logo"
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
    Row {
        Text(
            modifier = modifier.padding(start = 10.dp),
            text = nameOfTheTeam,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
        )


        Box(
            modifier = modifier
                .padding(start = 10.dp)
                .size(20.dp)
                .background(Color.DarkGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = winCount,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }

}


@Composable
@Preview
fun UpComingMatchDetailsPreview() {
    val context = LocalContext.current
    CenterAlignedTopAppBarExample("Chelsea", "Arsenal") {
        Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
    }
}

