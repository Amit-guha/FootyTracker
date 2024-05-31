package com.example.thefootballshow.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thefootballshow.R


@Composable
fun ShowLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val contentDesc = stringResource(R.string.loading)
        CircularProgressIndicator(modifier = Modifier
            .align(Alignment.Center)
            .semantics {
                contentDescription = contentDesc
            })
    }
}


@Composable
fun SetLeagueTitleText(modifier: Modifier = Modifier, leagueTitle: String) {
    Text(
        text = leagueTitle,
        modifier = modifier
            .wrapContentSize(align = Alignment.TopStart)
            .padding(start = 10.dp),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun UpcomingMatchesText(modifier: Modifier = Modifier) {
    Text(
        text = "Upcoming Match",
        modifier = modifier
            .wrapContentSize(align = Alignment.TopStart)
            .padding(start = 10.dp),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun SeeAllText(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Text(
        text = "See All",
        modifier = modifier
            .wrapContentSize(align = Alignment.TopStart)
            .padding(end = 10.dp)
            .clickable {
                onClick()
            },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyMedium
    )
}



@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ShowPreviewSetLeagueTitleText() {
    UpcomingMatchesText()
}



