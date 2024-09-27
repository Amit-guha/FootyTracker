package com.example.thefootballshow.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            PremierLeagueScreenRoute {
                navController.navigate(BottomBarScreen.PremierLeagueUpcomingMatchDetail.route)
                Log.d("UpcomingMatchList", "UpcomingMatchList: Clicked")
            }
        }

        composable(route = BottomBarScreen.SerieAScreen.route) {

        }

        composable(route = BottomBarScreen.BundesligaScreen.route) {

        }

        composable(route = BottomBarScreen.PremierLeagueUpcomingMatchDetail.route) {
            UpcomingMatchDetailRouteScreen()
        }

    }

}