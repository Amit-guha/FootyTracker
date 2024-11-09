package com.example.thefootballshow.data.model

data class LeagueTable(
    val position: String,
    val teamName: String,
    val playedGames: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int,
    val points: Int
)
