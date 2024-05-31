package com.example.thefootballshow.utils

enum class MatchStatus(val title: String) {
    UNKNOWN("UNKNOWN"),
    SCHEDULED("SCHEDULED"),
    LIVE("LIVE"),
    IN_PLAY("IN_PLAY"),
    PAUSED("PAUSED"),
    FINISHED("FINISHED"),
    POSTPONED("POSTPONED"),
    SUSPENDED("SUSPENDED"),
    CANCELED("CANCELED")
}