package com.example.thefootballshow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thefootballshow.ui.laligascreenroute.LaligaScreenRoute
import com.example.thefootballshow.ui.premierleaguescreenroute.PremierLeagueScreenRoute

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.LaligaScreen.route
    ) {

        composable(route = BottomBarScreen.LaligaScreen.route) {
            LaligaScreenRoute()
        }

        composable(route = BottomBarScreen.PremierLeagueScreen.route) {
            PremierLeagueScreenRoute()
        }

        composable(route = BottomBarScreen.SerieAScreen.route) {

        }

        composable(route = BottomBarScreen.BundesligaScreen.route) {

        }

    }

}