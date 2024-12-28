package com.example.thefootballshow.ui.upcomingMatchDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.enumUtills.FixturesEnum
import com.example.thefootballshow.utils.enumUtills.TeamStatEnum
import com.example.thefootballshow.utils.extension.loadAsyncImage
import com.example.thefootballshow.utils.extension.toAmPmFormat
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
    viewModel.getPreMatchDetailsInfo()


    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    val titleText = when (matchUiState) {
                        is UiState.Success -> {
                            val matchInfo = (matchUiState as UiState.Success<MatchInfo>).data
                            "${matchInfo.homeTeam.shortName} vs ${matchInfo.awayTeam.shortName}"
                        }

                        is UiState.Loading -> ""
                        is UiState.Error -> ""
                        else -> "Match Details"
                    }
                    Text(
                        text = titleText,
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
        Column(modifier = Modifier.padding(innerPadding)
           // .background(color = Color.White)
        ) {
            DisplayMatchDetails(matchUiState)

            //Maintain an Enum of H2H, Table, Lineups
            TeamStatus(onClickH2H = {},
                onClickTable = {},
                onClickLineUps = {})

            LeagueHeadLine(text = stringResource(R.string.lastFiveGames))
            CompetitionInfo(onAllCallback = {},
                onHomeCallback = {},
                onAwayCallback = {}
            )
            // TeamStatLazyColumn()
            // LeagueHeadLine(text = stringResource(R.string.league_table))
            // LeagueTableSeasonSpinner()
            // LeagueTable()
            DrawFootballField()


        }

    }
}

@Composable
fun DrawFootballField(modifier: Modifier = Modifier) {
    val color: Color = colorResource(id = R.color.gray_level_3)
    androidx.compose.foundation.Canvas(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val height = 800f
        val yLeftStart = 150f
        val smallYLeftStart = 250f
        val smallRectWidth = 120f
        val largeRectHeight = 200f


        drawParentRect(canvasWidth, height)
        drawPath(canvasWidth, height, color)
        drawPenaltyArea(yLeftStart, height)
        drawLeftPenaltyArc()
        drawLeftGoalArea(smallYLeftStart, smallRectWidth, height)


        drawCentreCircle(canvasWidth, height)
        drawCentreLine(canvasWidth, height)
        drawTextWithBackground(canvasWidth, height)


        drawRightPenaltyArea(canvasWidth, yLeftStart, height)
        drawRightGoalArea(canvasWidth, smallRectWidth, smallYLeftStart, height)

        drawRightPenaltyArc(canvasWidth)


    }

}


private fun DrawScope.drawRightPenaltyArc(canvasWidth: Float) {
    drawArc(
        color = Color.DarkGray,
        startAngle = 90f,
        sweepAngle = 180f,
        useCenter = false,
        style = Stroke(2.dp.toPx()),
        topLeft = Offset(canvasWidth - 290f, 300f),
        size = Size(150f, 150f),
    )
}


private fun DrawScope.drawRightPenaltyArea(
    canvasWidth: Float,
    yLeftStart: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(canvasWidth - 220f, yLeftStart),
        size = Size(220f, height - 2 * yLeftStart),
        style = Stroke(2.dp.toPx())
    )
}


private fun DrawScope.drawRightGoalArea(
    canvasWidth: Float,
    smallRectWidth: Float,
    smallYLeftStart: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(canvasWidth - smallRectWidth, smallYLeftStart),
        size = Size(smallRectWidth, height - 2 * smallYLeftStart),
        style = Stroke(2.dp.toPx())
    )
}


private fun DrawScope.drawTextWithBackground(
    canvasWidth: Float,
    height: Float
) {
    //Draw Text at centre
    val text = "MCI"
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 40f
        textAlign = android.graphics.Paint.Align.CENTER
    }

    // Measure the text's width and height for the background size
    val textWidth = textPaint.measureText(text)
    val textHeight = textPaint.fontMetrics.run { descent - ascent }
    val padding = 16f

    drawRect(
        color = Color.Yellow, // Background color for the text
        topLeft = Offset(
            canvasWidth / 2 - padding,
            height / 2 + textPaint.fontMetrics.ascent - padding
        ),
        size = Size(textWidth + 1.5f * padding, textHeight + 1.5f * padding)
    )

    drawContext.canvas.nativeCanvas.drawText(
        text,
        canvasWidth / 2 + 30f,
        height / 2,
        textPaint
    )

    val text2 = "Attack"
    val text2Width = textPaint.measureText(text2)
    val text2Height = textPaint.fontMetrics.run { descent - ascent }

    val y2 = height / 2 + textHeight + padding
    drawContext.canvas.nativeCanvas.drawText(
        text2,
        canvasWidth / 2 - 5f,
        y2,
        textPaint
    )
}


private fun DrawScope.drawCentreLine(
    canvasWidth: Float,
    height: Float
) {
    //Draw Line at Centre
    drawLine(
        color = Color.DarkGray,
        start = Offset(canvasWidth / 2, 0f),
        end = Offset(canvasWidth / 2, height),
        strokeWidth = 2.dp.toPx()
    )
}


private fun DrawScope.drawCentreCircle(
    canvasWidth: Float,
    height: Float
) {
    //Draw Circle at Centre
    drawCircle(
        color = Color.DarkGray,
        radius = 100f,
        center = Offset(canvasWidth / 2, height / 2),
        style = Stroke(2.dp.toPx())
    )
}

private fun DrawScope.drawLeftPenaltyArc() {

    //0 Degree means = 3'0 Clock
    //90 Degree means = 6'0 Clock
    //180 Degree means = 9'0 Clock
    //270 Degree means = 12'0 Clock
    //-180 Degree means = 12'0 Counter Clock

    drawArc(
        color = Color.DarkGray,
        startAngle = 270f,
        sweepAngle = 180f,
        useCenter = false,
        style = Stroke(2.dp.toPx()),
        topLeft = Offset(140f, 300f),
        size = Size(150f, 150f),
    )
}


private fun DrawScope.drawPenaltyArea(
    yLeftStart: Float,
    height: Float
) {
    //if we start from 100f then we also stop from same padding before full height
    //800, 100f, 800f + 100f = 900 - 2*100f = 700

    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(0f, yLeftStart),
        size = Size(220f, height - 2 * yLeftStart),
        style = Stroke(2.dp.toPx())
    )
}

private fun DrawScope.drawLeftGoalArea(
    smallYLeftStart: Float,
    smallRectWidth: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(0f, smallYLeftStart),
        size = Size(smallRectWidth, height - 2 * smallYLeftStart),
        style = Stroke(2.dp.toPx())
    )
}


private fun DrawScope.drawPath(
    canvasWidth: Float,
    height: Float,
    color: Color
) {
    val path = Path().apply {
        // Start from a point
        moveTo(0f, 0f)
        lineTo(canvasWidth / 2 + 120f, 0f)
        lineTo(canvasWidth / 2 + 180f, 100f)
        lineTo(canvasWidth / 2 + 120f, 200f)
        lineTo(canvasWidth / 2 + 180f, 300f)
        lineTo(canvasWidth / 2 + 120f, 400f)
        lineTo(canvasWidth / 2 + 180f, 500f)
        lineTo(canvasWidth / 2 + 120f, 600f)
        lineTo(canvasWidth / 2 + 180f, 700f)
        lineTo(canvasWidth / 2 + 120f, 800f)
        lineTo(0f, height)
        // close()
    }

    drawPath(
        path = path,
        color = color // Background color up to the path
    )

    drawPath(
        path = path,
        color = Color.Blue,
        style = Stroke(width = 2.dp.toPx()) // Set the stroke width
    )
}


private fun DrawScope.drawParentRect(
    canvasWidth: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(0f, 0f),
        size = Size(canvasWidth, height),
        style = Stroke(4.dp.toPx())
    )
}


@Composable
fun LeagueTable(modifier: Modifier = Modifier) {
    LazyColumn(modifier.horizontalScroll(rememberScrollState())) {
        items(5) {
            LeagueTableItem()
        }
    }
}

@Composable
fun LeagueTableItem(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp)
    ) {
        Text(
            "Position",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "Team",
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "MP",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "W",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "D",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "L",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "GF",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "GA",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "GD",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )

        Text(
            "Pts",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        )
    }
}


@Composable
fun TeamStatLazyColumn(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(top = 20.dp)) {
        items(5) {
            RecentTeamVsTeamStat()
        }
    }
}


@Composable
fun RecentTeamVsTeamStat(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(bottom = 5.dp)
    ) {
        TeamVsTeamResultInfo(0.4f, Arrangement.Center)
        TeamVsTeamDrawingRect(0.2f)
        TeamVsTeamResultInfo(0.4f, Arrangement.Center)
    }

}

@Composable
fun RowScope.TeamVsTeamDrawingRect(weight: Float) {
    Row(
        Modifier.weight(weight),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            Modifier
                .padding(start = 5.dp)
                .size(15.dp, 15.dp)
                .background(color = Color.Green, shape = RectangleShape)
        )

        Box(
            Modifier
                .padding(start = 5.dp)
                .size(15.dp, 15.dp)
                .background(color = Color.DarkGray, shape = RectangleShape)
        )
    }

}

@Composable
fun RowScope.TeamVsTeamResultInfo(
    weight: Float,
    horizontalArrangement: Arrangement.HorizontalOrVertical
) {
    Row(
        Modifier.weight(weight),
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            "MCI",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Red,
                fontWeight = FontWeight.Medium
            )
        )

        Text(
            "1 - 0",
            Modifier.padding(start = 5.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Text(
            "CHE",
            Modifier.padding(start = 5.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
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
                    color = if (selectedItem == FixturesEnum.ALL) Color.Blue else Color.DarkGray
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
                    color = if (selectedItem == FixturesEnum.HOME) Color.Blue else Color.DarkGray
                )
            )
            Text(
                stringResource(R.string.away),
                modifier = Modifier.weight(0.1f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = if (selectedItem == FixturesEnum.AWAY) Color.Blue else Color.DarkGray
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

    Row(
        modifier = Modifier
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
fun LeagueTitle(leagueName: String, url: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = leagueName.ifEmpty { "" },
            style = MaterialTheme.typography.headlineMedium
        )

        url.takeIf { it.isNotEmpty() }?.let {
            Modifier
                .padding(start = 10.dp)
                .width(44.dp)
                .height(44.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .loadAsyncImage(
                    url = url,
                    context = LocalContext.current,
                    contentDescription = "League Logo"
                )()
        }


        /*     Image(
                 modifier = modifier
                     .padding(start = 10.dp)
                     .width(44.dp)
                     .height(44.dp),
                 painter = painterResource(id = R.drawable.premier),
                 contentDescription = "League Logo"
             )*/
    }

}

@Composable
fun StadiumAndGameWeekDetails(
    stadiumName: String,
    currentMatchDay: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stadiumName.ifEmpty { "" },
            fontSize = 20.sp,
            color = Color.DarkGray,
        )

        Text(
            text = if (stadiumName.isNotEmpty()) " | " else "",
            fontSize = 25.sp,
            color = Color.DarkGray,
        )

        Text(
            text = if (currentMatchDay.isNotEmpty()) "CurrentMatchDay $currentMatchDay" else "",
            fontSize = 20.sp,
            color = Color.DarkGray,
        )

    }

}

@Composable
fun CompetitionBetweenTeamsTimeInfo(data: MatchInfo) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(1f)
    ) {
        HomeTeamVsAwayTeamLogo(data)
        //Spacer(modifier = Modifier.width(15.dp))
        TeamVsTeamLastWinningInfo(data)
        MatchStartTimeInfo(data = data)

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
fun RowScope.MatchStartTimeInfo(modifier: Modifier = Modifier,data: MatchInfo) {
    Box(
        modifier = modifier
            .weight(1f)
            .padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = modifier
        ) {
            Text(text = data.utcDate.takeIf { it.isNotEmpty() }?.toLocalDateAndMonth() ?: "")
            Text(
                text = data.utcDate.takeIf { it.isNotEmpty() }?.toLocalTime() ?: "",
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
fun RowScope.TeamVsTeamLastWinningInfo(data: MatchInfo) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTeamVsAwayTeamWinningStatistics(
            nameOfTheTeam = data.homeTeam.tla?:"",
            winCount = "2"
        )
        HomeTeamVsAwayTeamWinningStatistics(
            nameOfTheTeam = data.awayTeam.tla?:"",
            winCount = "10"
        )
    }

}


@Composable
private fun RowScope.HomeTeamVsAwayTeamLogo(data: MatchInfo) {
    Box(
        modifier = Modifier.weight(0.5f),
        contentAlignment = Alignment.CenterStart,

        ) {
        data.awayTeam.crest.takeIf { it.isNotEmpty() }?.let {
            Modifier
                .width(50.dp)
                .height(50.dp)
                .offset(x = 25.dp, y = 0.dp)
                .clip(CircleShape)
                .loadAsyncImage(
                    url = it,
                    context = LocalContext.current,
                    contentDescription = "Away Team Logo"
                )()
        } ?: Image(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .offset(x = 25.dp, y = 0.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.pl_main_logo),
            contentDescription = "Away Team Logo"
        )

        data.homeTeam.crest.takeIf { it.isNotEmpty() }?.let {
            Box {
                Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .loadAsyncImage(
                        url = it,
                        context = LocalContext.current,
                        contentDescription = "Home Team Logo"
                    )()
            }
        } ?: Box {
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
    CenterAlignedTopAppBarExample("Chelsea", "Arsenal", hiltViewModel()) {
        Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
    }
}



