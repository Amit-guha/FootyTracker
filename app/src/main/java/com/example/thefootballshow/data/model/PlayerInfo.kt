package com.example.thefootballshow.data.model

data class PlayerInfo(
    val id: Int? = null,
    val name: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null,
    val nationality: String? = null,
    val section: String? = null,
    val position: String? = null,
    val shirtNumber: Int? = null,
    val lastUpdated: String? = null,
    val currentTeam: CurrentTeamInfo? = null
)
