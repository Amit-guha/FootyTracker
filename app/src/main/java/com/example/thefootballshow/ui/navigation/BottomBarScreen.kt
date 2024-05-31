package com.example.thefootballshow.ui.navigation

import com.example.thefootballshow.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
     data object PremierLeagueScreen :
        BottomBarScreen("PL", "EPL", R.drawable.premier)

    data object SerieAScreen :
        BottomBarScreen("Serie A", "SERIE A", R.drawable.seria_a_logo)

    data object BundesligaScreen :
        BottomBarScreen("Bundesliga", "BUNDESLIGA", R.drawable.bundeshi)

    data object LaligaScreen : BottomBarScreen("LALIGA","Laliga",R.drawable.laliga_logotipo_1)
}



//PL - Premier league
// PD - Laliga
//SA - Seria A
//BL1 - Bundesliga
//FL1 - Ligue 1
//CL -  UEFA Champions League
//EC - European Championship