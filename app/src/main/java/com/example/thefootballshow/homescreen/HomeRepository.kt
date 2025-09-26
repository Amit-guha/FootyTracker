package com.example.thefootballshow.homescreen

import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.data.model.UpcomingMatches
import com.example.thefootballshow.ui.base.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTopLeague() : Flow<Resource<List<AreaCompetition>>>
    suspend fun getLiveMatches() : Flow<Resource<UpcomingMatches>>
    suspend fun getUpcomingMatches() : Flow<Resource<UpcomingMatches>>
}