package com.example.thefootballshow.ui.upcomingMatchDetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.utils.extension.loadAsyncImage
import com.example.thefootballshow.utils.extension.toAmPmFormat
import com.example.thefootballshow.utils.extension.toFriendlyDate

@Composable
fun MatchCard(data: MatchInfo, onClick: (Int, Int, Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
            .clickable {
                onClick(data.id, data.homeTeam.id, data.awayTeam.id)
                Log.d("FullCard", "FullCard: ${data.utcDate}")
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        HomeTeamVsAwayTeam(data)
    }
}

@Composable
fun HomeTeamVsAwayTeam(data: MatchInfo) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamMatchUpRow(data)
        FormatDayAndTime(data)
    }

}

@Composable
private fun FormatDayAndTime(data: MatchInfo) {
    DayText(dayDescription = data.utcDate.toFriendlyDate())
    TimeText(time = data.utcDate.toAmPmFormat())
}

@Composable
private fun TeamMatchUpRow(data: MatchInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        HomeTeamSection(data)
        VSText()
        AwayTeamSection(data)
    }
}

@Composable
private fun AwayTeamSection(data: MatchInfo) {
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClubImage(url = data.awayTeam.crest ?: "")
        ClubName(teamName = data.awayTeam.tla)
    }
}

@Composable
private fun HomeTeamSection(data: MatchInfo) {
    Column(
        modifier = Modifier.padding(start = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClubImage(url = data.homeTeam.crest ?: "")
        ClubName(teamName = data.homeTeam.tla)
    }
}

@Composable
fun ClubImage(url: String) {
    url.takeIf { it.isNotEmpty() }?.let {
        Modifier
            .width(44.dp)
            .height(44.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .loadAsyncImage(
                url = url,
                context = LocalContext.current,
                contentDescription = "League Logo"
            )()
    } ?: Image(
        painter = painterResource(id = R.drawable.premier_league_logo),
        contentDescription = "Premier League Logo",
        modifier = Modifier
            .size(44.dp)
            .padding(6.dp)
    )

}

@Composable
fun VSText(modifier: Modifier = Modifier) {
    Text(
        text = "Vs",
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(start = 10.dp)
    )
}

@Composable
fun ClubName(modifier: Modifier = Modifier, teamName: String) {
    Text(
        text = teamName,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 8.dp)
    )
}

@Composable
fun DayText(dayDescription: String) {
    Text(
        text = dayDescription.ifEmpty { "" },
        modifier = Modifier.padding(top = 20.dp),
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 15.sp,
        style = TextStyle(lineHeight = 0.sp)
    )
}

@Composable
fun TimeText(time: String) {
    Text(
        text = time.ifEmpty { "" },
        modifier = Modifier.padding(top = 2.dp, bottom = 15.dp),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            lineHeight = 0.sp,
            fontSize = MaterialTheme.typography.displayLarge.fontSize
        )

    )
}

@Preview
@Composable
private fun PreviewMatchCard() {
    //  MatchCard()
}

//https://dribbble.com/shots/15053294-Football-App