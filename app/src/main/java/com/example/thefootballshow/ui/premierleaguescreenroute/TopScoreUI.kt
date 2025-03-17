package com.example.thefootballshow.ui.premierleaguescreenroute

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.Scorer
import com.example.thefootballshow.utils.extension.loadAsyncImage

@Composable
fun TopScoreUI() {
    Text(
        text = stringResource(R.string.top_scorers),
        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun TopScorerItem(scorer: Scorer) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
        shape = RoundedCornerShape(15),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        ) {
            ClubImage(url = scorer.team?.crest ?: "")
            PlayerAndTeamInfo(scorer)
            RenderGoalUi(scorer)
            ArrowIcon()
        }

    }

}

@Composable
fun RowScope.ArrowIcon() {
    Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = "Arrow",
        modifier = Modifier
            .weight(0.5f)
    )
}

@Composable
fun RowScope.PlayerAndTeamInfo(scorer: Scorer) {
    Column(modifier = Modifier.weight(3f)) {
        Text(
            text = scorer.player?.name ?: "",
            style = MaterialTheme.typography.titleSmall
        )
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(
                top = 2.dp,
                bottom = 2.dp
            ),
        )

        Text(
            text = scorer.team?.shortName ?: "",
            style = TextStyle(
                fontSize = 10.sp
            )
        )
    }

    /*    Box(
            modifier = Modifier
                .padding(start = 5.dp)
                .size(34.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier) {
                drawCircle(
                    color = Color.LightGray, radius = 34f
                )
                drawCircle(
                    color = Color.Black, // Stroke color
                    radius = 34f,
                    style = Stroke(width = 4f, join = StrokeJoin.Round)
                )
            }
            Text(
                text = "20",
                style = MaterialTheme.typography.titleSmall
            )
        }*/
}


@Composable
private fun RenderGoalUi(scorer: Scorer) {
    Box(
        modifier = Modifier
            .padding(start = 5.dp)
            .size(40.dp)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            .background(shape = CircleShape, color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = scorer.goals.toString(),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun RowScope.ClubImage(url: String) {
    url.takeIf { it.isNotEmpty() }?.let {
        Modifier
            .weight(0.7f)
            .size(34.dp)
            .clip(CircleShape)
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


@Preview
@Composable
private fun ShowTopScoreUI() {

}