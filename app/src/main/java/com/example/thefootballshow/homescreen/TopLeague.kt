package com.example.thefootballshow.homescreen
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.ui.base.ShowLoading
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.ui.theme.Roboto
import com.example.thefootballshow.utils.extension.Dimens
import com.example.thefootballshow.utils.extension.Dimens.dp_12
import com.example.thefootballshow.utils.extension.Dimens.dp_26
import com.example.thefootballshow.utils.extension.Dimens.dp_5

@Composable
fun TopLeagues(
    topLeagues: UiState<List<AreaCompetition>>,
    onClick: (id: Int) -> Unit
) {
    when (topLeagues) {
        is UiState.Error -> {
            Text(topLeagues.message, color = Color.Red)
        }

        UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Success -> {
            val data = topLeagues.data
            LeagueList(data, onClick)
        }
    }
}



@Composable
fun LeagueList(
    data: List<AreaCompetition>,
    onClick: (Int) -> Unit
) {
    if (data.isEmpty()) {
        Text(text = "No leagues available", modifier = Modifier.padding(16.dp))
    } else {
        LazyRow(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(dp_5),
            contentPadding = PaddingValues(
                start = Dimens.PaddingMedium,
                top = dp_12,
                bottom = dp_26
            ),
        ) {
            items(data) { it ->
                RoundedLeagueItem(
                    areaCompetition = it,
                    onClick = { id ->
                        onClick(id)
                    }
                )
            }
        }
    }
}


@Composable
fun RoundedLeagueItem(
    areaCompetition: AreaCompetition,
    onClick: (id: Int) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (areaCompetition.isSelected) colorResource(R.color.ultramarine_blue)
        else colorResource(R.color.alice_blue)
    )

    val textColor by animateColorAsState(
        targetValue = if (areaCompetition.isSelected) Color.White else colorResource(R.color.outer_black)
    )

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .clickable {
                onClick(areaCompetition.id)
            }
            .padding(12.dp)

    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
            text = areaCompetition.name ?: "",
            fontSize = 12.sp,
            color = textColor,
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal
        )
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LeagueItemPreview(modifier: Modifier = Modifier) {
}