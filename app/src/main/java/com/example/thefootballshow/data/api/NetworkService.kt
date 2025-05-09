package com.example.thefootballshow.data.api

import com.example.thefootballshow.data.model.Competitions
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.Standings
import com.example.thefootballshow.data.model.TopScorer
import com.example.thefootballshow.data.model.UpcomingMatches
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetworkService {

    @GET("v4/competitions/{leagueId}/matches")
    suspend fun getUpcomingMatchList(
        @Path("leagueId") year: Int,
        @QueryMap params: Map<String, String>
    ): Response<UpcomingMatches>


    @GET("v4/matches/{competitionId}")
    suspend fun getPreMatchDetails(
        @Path("competitionId") competitionId: Int
    ): MatchInfo


    @GET("v4/teams/{teamId}/matches?status=FINISHED&season=2024&competitions&limit=5")
    suspend fun getLastFiveMatchInfo(
        @Path("teamId") teamId: Int
    ): UpcomingMatches

    @GET("v4/competitions/{competitionId}/standings")
    suspend fun getStandings(
        @Path("competitionId") competitionId: String,
        @Query("season") season: Int
    ): Standings


    @GET("v4/competitions")
    suspend fun getAllCompetitions(
        @Query("areas") areas: String
    ): Response<Competitions>

    @GET("v4/competitions/{leagueCode}/scorers")
    suspend fun getTopScorer(
        @Path("leagueCode") leagueCode : String,
        @Query("season") season: Int): TopScorer

}