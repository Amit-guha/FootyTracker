package com.example.thefootballshow.data.model

data class AreaCompetition(
    val id : Int?,
    val area: Area?,
    val name : String?,
    val code : String?,
    val type : String?,
    val emblem : String?,
    val plan : String?,
    val currentSeason : Season?,
    val numberOfAvailableSeasons : Int?,
    var isSelected : Boolean = false
)