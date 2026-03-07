package com.example.thefootballshow.data.model

data class Team(
    val crest: String?= null,
    val id: Int?=null,
    val name: String?=null,
    val shortName: String?=null,
    val tla: String?=null,
    var venue: String? = null,
    var address: String? = null,
    var website: String? = null,
    var founded: Int? = null,
    var clubColors: String? = null,
    var lastUpdated: String? = null
)