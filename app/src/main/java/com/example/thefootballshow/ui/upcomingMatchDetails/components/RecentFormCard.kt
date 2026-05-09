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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.RecentFormInfo
import com.example.thefootballshow.ui.theme.AppTheme
import com.example.thefootballshow.ui.theme.TheFootballShowTheme
import com.example.thefootballshow.ui.theme.White


@Composable
fun RecentFormCard(recentForm: RecentFormInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.recent_form),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Text(
                    text = stringResource(R.string.last_5_matches),
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = AppTheme.customColors.drawPrimary,
                        fontWeight = Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            TeamRecentForm(teamName = recentForm.homeTeamTla, form = recentForm.homeTeamForm)
            Spacer(modifier = Modifier.height(20.dp))
            TeamRecentForm(teamName = recentForm.awayTeamTla, form = recentForm.awayTeamForm)
        }
    }
}

@Composable
fun TeamRecentForm(teamName: String, form: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = teamName,
            modifier = Modifier.width(60.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AppTheme.customColors.teamNameColor
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            form.take(5).forEach { result ->
                FormBox(result)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun FormBox(result: String) {
    val backgroundColor = when (result.uppercase()) {
        "W" -> MaterialTheme.colorScheme.primary
        "L" -> MaterialTheme.colorScheme.onTertiary
        else -> MaterialTheme.colorScheme.secondaryContainer
    }
    val textColor = if (result.uppercase() == "D") AppTheme.customColors.drawPrimary else if (result.uppercase() == "L") White else AppTheme.customColors.winPrimary

    Box(
        modifier = Modifier
            .size(30.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = result.uppercase(),
            style = TextStyle(
                fontSize = 12.sp,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Composable
fun RecentFormCardPreviewLight() {
    val sampleData = RecentFormInfo(
        homeTeamTla = "ARS",
        homeTeamForm = listOf("W", "L", "D", "W", "W"),
        awayTeamTla = "CHE",
        awayTeamForm = listOf("L", "W", "D", "L", "W")
    )

    TheFootballShowTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        RecentFormCard(sampleData)
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun RecentFormCardPreviewDark() {
    val sampleData = RecentFormInfo(
        homeTeamTla = "ARS",
        homeTeamForm = listOf("W", "L", "D", "W", "W"),
        awayTeamTla = "CHE",
        awayTeamForm = listOf("L", "W", "D", "L", "W")
    )

    TheFootballShowTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        RecentFormCard(sampleData)
    }
}