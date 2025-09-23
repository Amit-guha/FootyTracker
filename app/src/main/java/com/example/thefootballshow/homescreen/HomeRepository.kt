package com.example.thefootballshow.homescreen

import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.ui.base.Resource
import com.example.thefootballshow.ui.base.UiState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTopLeague() : Flow<Resource<List<AreaCompetition>>>
    suspend fun getLiveMatches() : Flow<UiState<List<MatchInfo>>>
    suspend fun getUpcomingMatches() : Flow<UiState<List<MatchInfo>>>
}