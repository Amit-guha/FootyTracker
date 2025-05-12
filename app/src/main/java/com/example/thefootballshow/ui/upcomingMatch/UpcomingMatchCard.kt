package com.example.thefootballshow.ui.upcomingMatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.utils.extension.toAmPmFormat
import com.example.thefootballshow.utils.extension.toFriendlyDate


@Composable
fun FullCard(modifier: Modifier = Modifier, data: MatchInfo, onClick: (Int, Int, Int) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .clickable {
                onClick(data.id, data.homeTeam.id, data.awayTeam.id)
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
fun RowScope.HomeTeamCard(modifier: Modifier = Modifier, data: MatchInfo) {
    Box(
        modifier = modifier.weight(1f),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardImage(url = data.homeTeam.crest ?: "")
            ClubTextName(teamName = data.homeTeam.shortName)
        }
    }
}

@Composable
fun RowScope.AwayTeamCard(modifier: Modifier = Modifier, data: MatchInfo) {
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
fun RowScope.MatchTimeCard(modifier: Modifier = Modifier, data: MatchInfo) {
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
        url.takeIf { it.isNotEmpty() }?.let {
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
        } ?: Image(
            painter = painterResource(id = R.drawable.premier_league_logo),
            contentDescription = "Premier League Logo",
            modifier = Modifier
                .size(44.dp)
                .padding(6.dp)
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



