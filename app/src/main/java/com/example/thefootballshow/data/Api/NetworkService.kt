package com.example.thefootballshow.data.Api

import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.Standings
import com.example.thefootballshow.data.model.UpcomingMatches
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("v4/competitions/{year}/matches")
    suspend fun getUpcomingMatchList(
        @Path("year") year: Int,
        @Query("status") status: String
    ) : UpcomingMatches


    @GET("v4/matches/{competitionId}")
    suspend fun getPreMatchDetails(
        @Path("competitionId") competitionId : Int
    ) : MatchInfo


    @GET("v4/teams/{teamId}/matches?status=FINISHED&season=2024&competitions&limit=5")
    suspend fun getLastFiveMatchInfo(
        @Path("teamId") teamId : Int
    ) : UpcomingMatches

    @GET("v4/competitions/{competitionId}/standings")
    suspend fun getStandings(
        @Path("competitionId") competitionId : String,
        @Query("season") season : Int
    ) : Standings


}