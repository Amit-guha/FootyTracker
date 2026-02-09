package com.example.thefootballshow.data.model
data class CurrentTeamInfo(
    val area: Area? = null,
    val id: Int? = null,
    val name: String? = null,
    val shortName: String? = null,
    val tla: String? = null,
    val crest: String? = null,
    val address: String? = null,
    val website: String? = null,
    val founded: Int? = null,
    val clubColors: String? = null,
    val venue: String? = null,
    val runningCompetitions: List<Competition> = emptyList(),
    val contract: Contract? = null
)