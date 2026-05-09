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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.ui.theme.AppTheme
import com.example.thefootballshow.ui.theme.TheFootballShowTheme


@Composable
fun MomentumTrackerCard(
    values: List<Float>,
    modifier: Modifier = Modifier
) {
    val safeValues = values.take(10).map { it.coerceIn(0f, 1f) }
    val positiveColor = MaterialTheme.colorScheme.primary
    val negativeColor = AppTheme.customColors.negativeMomentumColor

    Column {
        Text(
            text = stringResource(R.string.momentum_tracker),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = AppTheme.customColors.teamNameColor
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = modifier,
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 18.dp)
                ) {
                    DashedMidLine(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        safeValues.forEachIndexed { index, value ->
                            val isNegativeRun = index in 4..5
                            MomentumBar(
                                modifier = Modifier.weight(1f),
                                value = value,
                                color = if (isNegativeRun) negativeColor else positiveColor,
                                maxHeight = 118.dp
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun MomentumBar(
    modifier: Modifier = Modifier,
    value: Float,
    color: Color,
    maxHeight: Dp
) {
    Box(
        modifier = modifier
            .height(maxHeight * value.coerceIn(0.1f, 1f))
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.92f))
    )
}



@Preview(name = "Momentum Tracker - Light", showBackground = true)
@Composable
fun MomentumTrackerCardPreviewLight() {
    TheFootballShowTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        MomentumTrackerCard(
            values = listOf(0.28f, 0.40f, 0.52f, 0.24f, 0.36f, 0.58f, 0.76f, 0.82f, 0.66f, 0.20f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(name = "Momentum Tracker - Dark", showBackground = true)
@Composable
fun MomentumTrackerCardPreviewDark() {
    TheFootballShowTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        MomentumTrackerCard(
            values = listOf(0.28f, 0.40f, 0.52f, 0.24f, 0.36f, 0.58f, 0.76f, 0.82f, 0.66f, 0.20f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}