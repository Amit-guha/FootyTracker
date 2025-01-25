package com.example.thefootballshow.ui.leagueTable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.Table
import com.example.thefootballshow.utils.extension.loadAsyncImage

@Composable
fun TeamStandingInLeague(table: Table, modifier: Modifier = Modifier) {
    val backgroundColor = when (table.position) {
        -1 -> colorResource(R.color.charleston_green)
        else -> colorResource(R.color.eerie_black)
    }

    Row(
        modifier = Modifier
            .background(backgroundColor)
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val positionText = if (table.position == -1) {
            ""
        } else {
            table.position.toString()
        }
        Text(
            positionText,
            modifier = modifier.weight(0.1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        table.team.crest.takeIf { it.isNotEmpty() }?.let {
            modifier
                .width(25.dp)
                .height(25.dp)
                .weight(0.1f)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .loadAsyncImage(
                    url = it,
                    context = LocalContext.current,
                    contentDescription = "League Logo"
                )()
        } ?: kotlin.run {
            Box(
                modifier = modifier
                    .width(25.dp)
                    .height(25.dp)
                    .weight(0.1f)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            ) {}
        }

        val teamAbbreviation = table.team.tla.ifEmpty { "" }
        Text(
            teamAbbreviation,
            modifier = modifier
                .weight(0.1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        val playedGamesText = if (table.playedGames == -1) {
            "PL"
        } else {
            table.playedGames.toString()
        }
        Text(
            playedGamesText,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .weight(0.1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        val winsText = if (table.won == -1) {
            "W"
        } else {
            table.won.toString()
        }
        Text(
            winsText,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .weight(0.1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        val drawsText = if (table.draw == -1) {
            "D"
        } else {
            table.draw.toString()
        }
        Text(
            drawsText,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .weight(0.1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        val lossesText = if (table.lost == -1) {
            "L"
        } else {
            table.lost.toString()
        }
        Text(
            lossesText,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .weight(0.1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        /*    val goalsFor = if (table.goalsFor == -1) {
                "GF"
            } else {
                table.goalsFor.toString()

            }
            Text(
                "GF",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = modifier
                    .weight(0.1f)
                    .padding(start = 10.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            )*/

        /*    val goalsAgainst = if (table.goalsAgainst == -1) {
                "GA"
            } else {
                table.goalsAgainst.toString()
            }
            Text(
                "GA",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = modifier
                    .weight(0.1f)
                    .padding(start = 10.dp)
                    ,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            )*/

        val goalsDifferenceText = if (table.goalDifference == -1) {
            "GD"
        } else {
            table.goalDifference.toString()
        }

        Text(
            goalsDifferenceText,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .weight(0.1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

        val pointsText = if (table.points == -1) {
            "PTS"
        } else {
            table.points.toString()
        }

        Text(
            pointsText,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = modifier
                .weight(0.1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
    }

    Row {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
        )

        /*        HorizontalDivider(
                    modifier = modifier.fillMaxWidth(),
                    thickness = 3.dp,
                    color = colorResource(R.color.black)
                )*/
    }


}


@Composable
@Preview(showBackground = true)
fun ShowTablePreview(modifier: Modifier = Modifier) {
    //Table(modifier = Modifier.fillMaxWidth())
}