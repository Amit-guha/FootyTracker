package com.example.thefootballshow.ui.profile

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.Player
import com.example.thefootballshow.data.model.Scorer
import com.example.thefootballshow.data.model.Team
import com.example.thefootballshow.data.model.areaList._response.AreaInfo
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.extension.loadAsyncImage
import com.example.thefootballshow.utils.extension.showLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                val middleOfScreen = LocalConfiguration.current.screenHeightDp.dp / 2
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
                        imageUrl = null,
                        clubLogoUrl = data?.team?.crest,
                        size = imageSize,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = imageSize / 2)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .offset(y = imageSize / -4)
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LoveButton(middleOfTheScreen = middleOfScreen)
                }

                Spacer(modifier = Modifier.height(imageSize / 1.5f))
                PlayerInfo(data)
                Spacer(modifier = Modifier.height(height = 40.dp))
                PlayerStatsAnimation(
                    goals = data?.goals ?: 0,
                    assists = data?.assists ?: 0,
                    played = data?.playedMatches ?: 0
                )

                Spacer(modifier = Modifier.height(height = 40.dp))
                PlayerAttributesSection(data?.player?.section ?: "")
            }
        }
    }


}

@Composable
fun PlayerInfo(data : Scorer?) {
    Column {
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
    }
}

@Composable
fun PlayerAttributesSection(playingStyle: String) {
    var technical by remember { mutableFloatStateOf(0f) }
    var speed by remember { mutableFloatStateOf(0f) }
    var agility by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        delay(200)
        technical = 0.85f

        delay(200)
        speed = 0.72f

        delay(200)
        agility = 0.90f
    }

    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        PlayingStyle(playingStyle)
        Spacer(Modifier.height(10.dp))
        PlayerAttributes(
            technical = technical,
            speed = speed,
            agility = agility
        )
    }

}


@Composable
fun PlayerAttributes(
    technical: Float,
    speed: Float,
    agility: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AttributeItem("Technical", technical)
        AttributeItem("Speed", speed)
        AttributeItem("Agility", agility)
    }
}

@Composable
fun ProfilePicture(
    imageUrl: String?,
    clubLogoUrl : String?,
    modifier: Modifier = Modifier,
    size: Dp = 72.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier.size(size),
            shape = CircleShape,
            color = Color.White,
            tonalElevation = 2.dp,
            shadowElevation = 8.dp,
            border = BorderStroke(
                width = 1.dp,
                color = Color.White
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
                    Image(
                        painter = painterResource(R.drawable.ic_football_player),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(size-20.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }


        clubLogoUrl?.let {
            Surface(
                modifier = Modifier
                    .size(size / 2)
                    .offset(x= (size / 3),y = (size / 3)),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                border = BorderStroke(1.dp, Color.LightGray)
            ){
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ){
                    Modifier
                        .size(30.dp)
                        .loadAsyncImage(
                            url = it,
                            context = LocalContext.current,
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )()
                }

            }
        }
    }
}

@Composable
fun PlayingStyle(playingStyle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = "STYLE :  ",
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            textAlign = TextAlign.Center,
            text = playingStyle,
            color = Color.DarkGray,
            fontFamily = FontFamily.SansSerif,
        )
    }
}

@Composable
fun PlayerStatsAnimation(
    goals: Int,
    assists: Int,
    played: Int
) {

    var goalsCount by remember { mutableIntStateOf(0) }
    var assistsCount by remember { mutableIntStateOf(0) }
    var playedCount by remember { mutableIntStateOf(0) }

    val goalsAnim by animateIntAsState(targetValue = goalsCount)
    val assistsAnim by animateIntAsState(targetValue = assistsCount)
    val playedAnim by animateIntAsState(targetValue = playedCount)


    LaunchedEffect(Unit) {
        for (i in 1..goals) {
            goalsCount = i
            delay(50)
        }

        for (i in 1..assists) {
            assistsCount = i
            delay(50)
        }
        for (i in 1..played) {
            playedCount = i
            delay(50)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(label = stringResource(R.string.goals), value = goalsAnim)
        VerticalDividerItem()
        StatItem(label = stringResource(R.string.assists), value = assistsAnim)
        VerticalDivider()
        StatItem(label = stringResource(R.string.played), value = playedAnim)
    }
}

@Composable
fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary,
    backgroundColor: Color = Color.LightGray
) {
    // Animate the progress smoothly
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = modifier
            .height(15.dp)
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .background(color)
        )
    }
}

@Composable
fun AttributeItem(
    label: String,
    progress: Float,
) {
    Column {
        AnimatedProgressBar(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            maxLines = 1,
            style = TextStyle(
                fontWeight = Medium,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            ),
            text = label
        )
    }
}



@Composable
fun VerticalDividerItem() {
    VerticalDivider(
        Modifier
            .fillMaxHeight(),
        color = Color.LightGray,
        thickness = 1.dp
    )
}

@Composable
fun StatItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$value",
            style = TextStyle(
                fontWeight = Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = label,
            fontWeight = FontWeight.Light,
            color = Color.DarkGray,
            fontFamily = FontFamily.SansSerif,
        )
    }
}


@Composable
fun LoveButton(
    size: Dp = 48.dp,
    middleOfTheScreen : Dp,
    modifier: Modifier = Modifier
) {
    var triggerAnimation by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        repeat(triggerAnimation) {
            FloatingHeart(
                middleOfTheScreen = middleOfTheScreen,
                onAnimationEnd = { triggerAnimation-- }
            )
        }

        Surface(
            modifier = Modifier
                .size(size)
                .clickable{
                    triggerAnimation += 5
                },
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 8.dp,
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Love",
                    tint = Color.Red,
                    modifier = Modifier.size(size * 0.5f)
                )
            }
        }
    }
}





@Composable
fun FloatingHeart(
    middleOfTheScreen : Dp,
    onAnimationEnd: () -> Unit
) {
    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(Random.nextFloat() * 500f - 300f) }
    val alpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        launch {
            offsetY.animateTo(
                targetValue = -middleOfTheScreen.value,
                animationSpec = tween(2500)
            )
        }

        launch {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(2500)
            )
        }

        delay(2500)
        onAnimationEnd()
    }

    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        tint = Color.Red,
        modifier = Modifier
            .size(36.dp)
            .graphicsLayer {
                translationY = offsetY.value
                translationX = offsetX.value
                this.alpha = alpha.value
            }
    )
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