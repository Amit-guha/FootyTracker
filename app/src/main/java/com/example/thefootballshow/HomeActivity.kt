package com.example.thefootballshow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.homescreen.HomeScreen
import com.example.thefootballshow.ui.commonui.SystemBarColor
import com.example.thefootballshow.ui.theme.Oswald
import com.example.thefootballshow.ui.theme.TheFootballShowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        setContent {
            TheFootballShowTheme {
                val scrollBehavior =
                    TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "The Football Show",
                                    style = TextStyle(
                                        fontFamily = Oswald,
                                        fontSize = 18.sp,
                                    ),
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = androidx.compose.ui.graphics.Color.White,
                                titleContentColor = androidx.compose.ui.graphics.Color.Black,
                            ),
                            actions = {
                                IconButton(onClick = { }) {
                                    Icon(
                                        modifier = Modifier.
                                            size(48.dp).
                                        padding(end = 25.dp),
                                        painter = painterResource(R.drawable.ic_bell),
                                        contentDescription = "Localized description",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )
                    }
                ) { innerPadding ->
                    SystemBarColor(
                        statusBarColor = androidx.compose.ui.graphics.Color.White,
                        darkIcons = true
                    )
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onBackground,
                            thickness = 1.dp,
                            modifier = Modifier
                        )
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    TheFootballShowTheme {
        Greeting2("Android")
    }
}