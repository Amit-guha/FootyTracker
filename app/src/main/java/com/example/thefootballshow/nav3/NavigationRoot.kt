package com.example.thefootballshow.nav3

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.thefootballshow.ui.laligascreenroute.LaligaScreenRoute
import com.example.thefootballshow.ui.premierleaguescreenroute.PremierLeagueScreenRoute
import com.example.thefootballshow.utils.enumUtills.MatchTypeEnum

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Route.HOME)

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = { key ->
            when(key){
                 Route.HOME -> NavEntry(key){
                    PremierLeagueScreenRoute(onItemClick = { /*matchNavParams->
                        when(matchNavParams.matchEnum){
                            MatchTypeEnum.MATCH_DETAILS_ENUM -> {
                                Log.d("UpcomingMatchList", "UpcomingMatchList: Clicked")
                            }
                            MatchTypeEnum.ALL_MATCH_ENUM -> {
                            }
                        }*/

                    }

                    )

                }
                is Route.MATCH_DETAILS -> NavEntry(key){
                    LaligaScreenRoute()
                }

                else ->  error("Unknown route: $key")

            }
        }
    )

}