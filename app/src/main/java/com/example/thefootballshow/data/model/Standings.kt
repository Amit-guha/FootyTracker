package com.example.thefootballshow.data.model

data class Standings(
    val area: Area,
    val competition: Competition,
    val filters: Filters,
    val season: Season,
    val standings: List<Standing>
)