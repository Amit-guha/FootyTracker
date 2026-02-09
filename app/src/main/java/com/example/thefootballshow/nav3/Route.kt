package com.example.thefootballshow.nav3
import androidx.navigation3.runtime.NavKey
import com.example.thefootballshow.data.model.Scorer
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey{
    @Serializable
    data object HOME : Route


    data object UPCOMING_MATCHES : Route


    data object PLAYERS :Route


    data object TEAMS :Route


    data class MATCH_DETAILS(val competitionId : Int, val homeTeamId : Int, val awayTeamId : Int) : Route


    data class PLAYER_DETAILS(
        val scorer : Scorer
    ) :Route

}