package com.example.thefootballshow.ui.profile

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.thefootballshow.data.model.Player
import com.example.thefootballshow.data.model.Scorer
import com.example.thefootballshow.data.model.Team
import com.example.thefootballshow.data.model.areaList._response.AreaInfo
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.extension.ageText
import com.example.thefootballshow.utils.extension.loadAsyncImage
import com.example.thefootballshow.utils.extension.showLog
import kotlinx.coroutines.delay

@Composable
fun PlayerProfileRoute(
    modifier: Modifier = Modifier,
    scorer: Scorer,
    viewModel: PlayerProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    context.showLog(tag = "PlayerProfile --->", message = "$scorer")

    LaunchedEffect(scorer) {
        viewModel.initWithPlayerInfo(scorer)
    }

    val playerProfileInfo by viewModel.playerProfileInfo.collectAsStateWithLifecycle()
    val areaInfo by viewModel.area.collectAsStateWithLifecycle()

    PlayerProfile(
        playerProfileInfo = playerProfileInfo,
        areaInfo = areaInfo
    )

}

@Composable
fun PlayerProfile(playerProfileInfo: UiState<Scorer>, areaInfo: UiState<AreaInfo>) {

    val area: AreaInfo? = when (areaInfo) {
        is UiState.Error -> {
            null
        }

        UiState.Loading -> {
            null
        }

        is UiState.Success<*> -> {
            areaInfo.data as AreaInfo
        }
    }

    when (playerProfileInfo) {
        is UiState.Error -> {}
        UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success<*> -> {
            val data = playerProfileInfo.data as? Scorer
            val imageSize = 90.dp
            Column(verticalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),

                    ) {
                    area?.flagUrl?.let {
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp).loadAsyncImage(
                                url = it,
                                context = LocalContext.current,
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth
                            )()
                    } ?: Modifier.background(Color.Red)

                    ProfilePicture(
                        fullName = data?.player?.name ?: "",
                        imageUrl = null,
                        size = imageSize,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = imageSize / 2)
                    )
                }

                Spacer(modifier = Modifier.height(imageSize / 1.5f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontWeight = Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    ),
                    text = data?.player?.name ?: ""
                )

                Spacer(modifier = Modifier.height(height = 10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                ) {
                    data?.player?.position?.let {
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = Medium
                            ),
                            text = "Position : $it"
                        )

                        Text(
                            style = TextStyle(
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            ),
                            text = "|"
                        )
                    }
                    
                        data?.player?.dateOfBirth?.let {
                            Text(
                                maxLines = 1,
                                style = TextStyle(
                                    fontWeight = Medium,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                ),
                                text = "DOB : $it"
                            )
                        }
                }

                Spacer(modifier = Modifier.height(height = 3.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                ) {
                    data?.player?.nationality?.let {
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = Medium
                            ),
                            text = "Country : $it"
                        )

                        Text(
                            style = TextStyle(
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            ),
                            text = "|"
                        )
                    }

                    data?.team?.name?.let {
                        Text(
                            maxLines = 1,
                            style = TextStyle(
                                fontWeight = Medium,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            ),
                            text = "Club : $it"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(height = 20.dp))
                PlayerStatsAnimation(
                    goals = data?.goals ?: 0,
                    assists = data?.assists ?: 0,
                    played = data?.playedMatches ?: 0
                )

            }
        }
    }


}

@Composable
fun ProfilePicture(
    imageUrl: String?,
    fullName: String,
    modifier: Modifier = Modifier,
    size: Dp = 72.dp
) {
    val initials = remember(fullName) {
        fullName
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = Modifier.size(size),
            shape = CircleShape,
            color = Color.White,
            tonalElevation = 2.dp,
            shadowElevation = 8.dp,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseSurface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (!imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = initials,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }


        /* Box(
             modifier = Modifier
                 .size(size)
                 .border(
                     width = 2.dp,
                     color = MaterialTheme.colorScheme.primary,
                     shape = CircleShape
                 )
                 .clip(CircleShape)
                 .background(Color.White),
             contentAlignment = Alignment.Center
         ) {
             if (!imageUrl.isNullOrBlank()) {
                 AsyncImage(
                     model = imageUrl,
                     contentDescription = "Profile Picture",
                     contentScale = ContentScale.Crop,
                     modifier = Modifier.fillMaxSize()
                 )
             } else {
                 Text(
                     text = initials,
                     style = MaterialTheme.typography.titleMedium,
                     color = MaterialTheme.colorScheme.primary
                 )
             }
         }*/


    }
}

@Composable
fun PlayerStatsAnimation(
    goals: Int,
    assists: Int,
    played: Int
) {
    // State for each counter
    var goalsCount by remember { mutableStateOf(0) }
    var assistsCount by remember { mutableStateOf(0) }
    var playedCount by remember { mutableStateOf(0) }

    // Animated values
    val goalsAnim by animateIntAsState(targetValue = goalsCount)
    val assistsAnim by animateIntAsState(targetValue = assistsCount)
    val playedAnim by animateIntAsState(targetValue = playedCount)

    // Launch sequential animation
    LaunchedEffect(Unit) {
        // Animate goals
        for (i in 1..goals) {
            goalsCount = i
            delay(50)
        }

        // Animate assists
        for (i in 1..assists) {
            assistsCount = i
            delay(50)
        }

        // Animate played matches
        for (i in 1..played) {
            playedCount = i
            delay(50)
        }
    }

    // UI
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(label = "Goals", value = goalsAnim)
        StatItem(label = "Assists", value = assistsAnim)
        StatItem(label = "Played", value = playedAnim)
    }
}

@Composable
fun StatItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$value",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PlayerProfilePreview() {
    PlayerProfile(
        playerProfileInfo = UiState.Success(
            data = Scorer(
                player = Player(
                    id = 1,
                    name = "Lionel Messi",
                    position = "Midfielder",
                    nationality = "Argentina",
                    dateOfBirth = "1987-06-24",
                    shirtNumber = 10,
                    firstName = "Lionel",
                    lastName = "Andrés",
                    section = "First Team",
                    lastUpdated = "2025-01-01T00:00:00Z"
                ),
                team = Team(
                    id = 1,
                    name = "Paris Saint-Germain",
                    venue = "Parc des Princes",
                    crest = "https://crests.football-data.org/61.png",
                    founded = 1970,
                    shortName = "Messi",
                    tla = "PSG",
                ),
                goals = 10,
                assists = 5,
                penalties = 15,
                playedMatches = 20
            )
        ),

        areaInfo = UiState.Success(
            data = AreaInfo(
                id = 1,
                name = "Argentina",
                countryCode = "AR",
            )
        )
    )
}