package com.example.thefootballshow.data.model

data class UpcomingMatches(
    val competition: Competition,
    val filters: Filters,
    val matches: List<Matche>,
    val resultSet: ResultSet
)