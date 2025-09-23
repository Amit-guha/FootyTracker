package com.example.thefootballshow.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thefootballshow.R
import com.example.thefootballshow.ui.theme.Oswald
import com.example.thefootballshow.ui.theme.Roboto
import com.example.thefootballshow.utils.extension.Dimens
import com.example.thefootballshow.utils.extension.Dimens.ImageMedium
import com.example.thefootballshow.utils.extension.Dimens.PaddingMedium
import com.example.thefootballshow.utils.extension.Dimens.dp_20
import com.example.thefootballshow.utils.extension.TextSizes
import com.example.thefootballshow.utils.extension.TextSizes.Small
import com.example.thefootballshow.utils.extension.TextSizes.Sp_14


@Composable
fun UpcomingMatchList() {
    LazyColumn(
        contentPadding = PaddingValues(PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(PaddingMedium)
    ) {
        items(2) {
            UpcomingMatchCard()
        }
    }
}

@Composable
fun UpcomingMatchCard() {
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(Dimens.BorderMedium),
        colors = CardDefaults.cardColors(colorResource(R.color.light_blue)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingMedium)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                // Match Time
                Text(
                    text = "20:45",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Medium,
                        fontSize = Small,
                        textAlign = TextAlign.Center
                    )
                )

                //League Title
                Text(
                    text = "Premier League",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Medium,
                        fontSize = Small,
                        textAlign = TextAlign.Center
                    )
                )

            }

            // Home Team Vs Away Team
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PaddingMedium, end = PaddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f, fill = true),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /* areaCompetition.emblem?.takeIf { it.isNotEmpty() }?.let {
                         Modifier
                             .size(24.dp)
                             .clip(CircleShape)
                             .loadAsyncImage(
                                 url = it,
                                 context = LocalContext.current,
                                 contentDescription = "Away Team Logo"
                             )()
                     }?:*/
                    Image(
                        painter = painterResource(id = R.drawable.premier_league_logo),
                        contentDescription = "Premier League Logo",
                        modifier = Modifier
                            .size(ImageMedium)
                            .clip(CircleShape)
                    )

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Man City",
                        color = androidx.compose.ui.graphics.Color.Black,
                        style = TextStyle(
                            fontFamily = Roboto,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = TextSizes.Medium,
                            textAlign = TextAlign.Center,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier.weight(1f, fill = true),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(R.string.vs),
                            color = colorResource(R.color.black_coral),
                            style = TextStyle(
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Bold,
                                fontSize = TextSizes.Large,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = stringResource(R.string.upcoming),
                            color = colorResource(R.color.space),
                            style = TextStyle(
                                fontFamily = Roboto,
                                fontWeight = FontWeight.Normal,
                                fontSize = Small,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }


                Column(
                    modifier = Modifier.weight(1f, fill = true),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /* areaCompetition.emblem?.takeIf { it.isNotEmpty() }?.let {
                         Modifier
                             .size(24.dp)
                             .clip(CircleShape)
                             .loadAsyncImage(
                                 url = it,
                                 context = LocalContext.current,
                                 contentDescription = "Away Team Logo"
                             )()
                     }?:*/
                    Image(
                        painter = painterResource(id = R.drawable.premier_league_logo),
                        contentDescription = "Premier League Logo",
                        modifier = Modifier
                            .size(ImageMedium)
                            .clip(CircleShape)
                    )

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Man City",
                        color =
                            Color.Black,
                        style = TextStyle(
                            fontFamily = Roboto,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = TextSizes.Medium,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

            HorizontalDivider(
                modifier = Modifier.padding(top = PaddingMedium, bottom = dp_20),
                thickness = 2.dp,
                color = colorResource(R.color.platinum)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Premier League",
                    color = Color.Black,
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W400,
                        fontSize = Small,
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.details),
                    color = colorResource(R.color.cornflower_Blue),
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W400,
                        fontSize = Sp_14,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun UpcomingMatchCardPreview() {
    UpcomingMatchCard()
}