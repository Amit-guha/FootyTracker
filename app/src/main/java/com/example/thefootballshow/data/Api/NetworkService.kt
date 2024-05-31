package com.example.thefootballshow.data.Api

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

}