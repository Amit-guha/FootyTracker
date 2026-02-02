package com.example.thefootballshow.nav3

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.thefootballshow.ui.premierleaguescreenroute.PremierLeagueScreenRoute
import com.example.thefootballshow.ui.upcomingMatch.BuildUpcomingMatchesUI
import com.example.thefootballshow.ui.upcomingMatchDetails.UpcomingMatchDetailRouteScreen
import com.example.thefootballshow.utils.enumUtills.MatchTypeEnum

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Route.HOME)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                Route.HOME -> NavEntry(key) {
                    PremierLeagueScreenRoute(onItemClick = { matchNavParams ->
                        when (matchNavParams.matchEnum) {
                            MatchTypeEnum.MATCH_DETAILS_ENUM -> {
                                backStack.add(
                                    Route.MATCH_DETAILS(
                                        competitionId = matchNavParams.competitionId ?: 0,
                                        homeTeamId = matchNavParams.homeTeamId ?: 0,
                                        awayTeamId = matchNavParams.awayTeamId ?: 0
                                    )
                                )
                                Log.d("UpcomingMatchList", "UpcomingMatchList: Clicked")
                            }

                            MatchTypeEnum.ALL_MATCH_ENUM -> {
                                backStack.add(Route.UPCOMING_MATCHES)
                            }
                        }

                    }

                    )

                }

                is Route.MATCH_DETAILS -> NavEntry(key) {
                    UpcomingMatchDetailRouteScreen(
                        competitionId = key.competitionId,
                        homeTeamId = key.homeTeamId,
                        awayTeamId = key.awayTeamId
                    ) {}
                }

                is Route.UPCOMING_MATCHES -> NavEntry(key) {
                    BuildUpcomingMatchesUI()
                }

                else -> error("Unknown route: $key")

            }
        }
    )

}