package com.example.thefootballshow.ui.upcomingMatchDetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.ui.theme.AppTheme
import com.example.thefootballshow.ui.theme.TheFootballShowTheme

@Composable
fun HeadToHeadCard(
    homeTeamName: String,
    awayTeamName: String,
    homeWins: Int,
    awayWins: Int,
    draws: Int,
    modifier: Modifier = Modifier
) {
    val totalMatches by remember { mutableIntStateOf(homeWins + awayWins + draws) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.head_to_head),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = stringResource(R.string.matches, totalMatches),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            H2HStatRow(
                label = stringResource(R.string.wins, homeTeamName),
                value = homeWins,
                total = totalMatches,
                color = MaterialTheme.colorScheme.primary

            )
            Spacer(modifier = Modifier.height(20.dp))
            H2HStatRow(
                label = "DRAWS",
                value = draws,
                total = totalMatches,
                color = AppTheme.customColors.drawProgressBar
            )

            Spacer(modifier = Modifier.height(20.dp))
            H2HStatRow(
                label = stringResource(R.string.wins, awayTeamName),
                value = awayWins,
                total = totalMatches,
                color = AppTheme.customColors.awayTeamProgressBar
            )
        }
    }
}

@Composable
private fun H2HStatRow(
    label: String,
    value: Int,
    total: Int,
    color: Color
) {
    val progress = if (total > 0) value.toFloat() / total else 0f
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = value.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
    }
}

@Preview
@Composable
fun HeadToHeadCardPreview() {
    TheFootballShowTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            HeadToHeadCard(
                homeTeamName = "MUN",
                awayTeamName = "LIV",
                homeWins = 12,
                awayWins = 8,
                draws = 4
            )
        }
    }

}

@Preview
@Composable
fun HeadToHeadCardDarkPreview() {
    TheFootballShowTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            HeadToHeadCard(
                homeTeamName = "MUN",
                awayTeamName = "LIV",
                homeWins = 12,
                awayWins = 8,
                draws = 4
            )
        }
    }

}
