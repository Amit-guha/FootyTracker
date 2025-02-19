package com.example.thefootballshow.data.model

import com.google.gson.annotations.SerializedName

data class Competitions(
    val count: Int,
   // val filters: Filters,
    @SerializedName("competitions")
    val competitions: MutableList<AreaCompetition>
)
