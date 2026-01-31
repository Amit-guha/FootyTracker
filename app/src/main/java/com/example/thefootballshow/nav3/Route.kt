package com.example.thefootballshow.nav3
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey{
    @Serializable
    data object HOME : Route


    data object UPCOMING_MATCHES : Route


    data object PLAYERS :Route


    data object TEAMS :Route


    data object MATCH_DETAILS : Route


    data object PLAYER_DETAILS :Route

}