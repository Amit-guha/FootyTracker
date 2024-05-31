package com.example.thefootballshow.ui.upcomingMatchDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R


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
        }

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
@Preview
fun UpComingMatchDetailsPreview() {
    val context = LocalContext.current
    CenterAlignedTopAppBarExample("Chelsea", "Arsenal") {
        Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
    }
}

