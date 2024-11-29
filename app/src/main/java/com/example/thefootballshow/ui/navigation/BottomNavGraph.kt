package com.example.thefootballshow.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thefootballshow.ui.laligascreenroute.LaligaScreenRoute
import com.example.thefootballshow.ui.premierleaguescreenroute.PremierLeagueScreenRoute
import com.example.thefootballshow.ui.upcomingMatchDetails.UpcomingMatchDetailRouteScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.PremierLeagueScreen.route
    ) {

        composable(route = BottomBarScreen.LaligaScreen.route) {
            LaligaScreenRoute()
        }

        composable(route = BottomBarScreen.PremierLeagueScreen.route) {
            PremierLeagueScreenRoute(onItemClick = {competitionId,homeTeamId,awayTeamId ->
                navController.navigate("${BottomBarScreen.PremierLeagueUpcomingMatchDetail.route}/${competitionId}/${homeTeamId}/${awayTeamId}")
                Log.d("UpcomingMatchList", "UpcomingMatchList: Clicked")
            })
        }

        composable(route = BottomBarScreen.SerieAScreen.route) {

        }

        composable(route = BottomBarScreen.BundesligaScreen.route) {

        }

        composable(route = "${BottomBarScreen.PremierLeagueUpcomingMatchDetail.route}/{competitionId}/{homeTeamId}/{awayTeamId}",
            arguments = listOf(navArgument(
                "competitionId"
            ){
                type = NavType.IntType
            },
                navArgument("homeTeamId"){
                    type = NavType.IntType
                },
                navArgument("awayTeamId"){
                    type = NavType.IntType
                }
            )
        ) {

            val competitionId = it.arguments?.getInt("competitionId")?:-1
            val homeTeamId = it.arguments?.getInt("homeTeamId")?:-1
            val awayTeamId = it.arguments?.getInt("awayTeamId")?:-1
            Log.d("Argument : ","$competitionId $homeTeamId $awayTeamId")
            UpcomingMatchDetailRouteScreen(competitionId = competitionId, homeTeamId = homeTeamId, awayTeamId = awayTeamId)
        }

    }

}