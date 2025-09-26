package com.example.thefootballshow.data.model

data class Score(
    val duration: String,
    val fullTime: FullTime,
    val halfTime: HalfTime,
    val regularTime: RegularTime,
    val extraTime: ExtraTime,
    val penalties: Penalties,
    val winner: Any
)