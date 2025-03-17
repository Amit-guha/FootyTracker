package com.example.thefootballshow.data.model

import com.google.gson.annotations.SerializedName

data class TopScorer(
    var count: Int,
    @SerializedName("filters")
    var filters: ScorerFilter,
    var competition: Competition,
    var season: Season,
    @SerializedName("scorers")
    var scorers: List<Scorer>
)

data class ScorerFilter(
    val season: Int?,
    val limit: Int?
)

data class Scorer(
    @SerializedName("player")
    var player: Player?,
    var team: Team?,
    var playedMatches: Int?,
    var goals: Int?,
    var assists: Int?,
    var penalties: Int?
)

data class Player(
    var id: Int?,
    var name: String?,
    var firstName: String?,
    var lastName: String?,
    var dateOfBirth: String?,
    var nationality: String?,
    var section: String?,
    var position: String?,
    var shirtNumber: Int?,
    var lastUpdated: String?
)
