package com.example.thefootballshow.data.model

data class Team(
    val crest: String,
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    var venue: String? = null,
    var address: String? = null,
    var website: String? = null,
    var founded: Int? = null,
    var clubColors: String? = null,
    var lastUpdated: String? = null
)