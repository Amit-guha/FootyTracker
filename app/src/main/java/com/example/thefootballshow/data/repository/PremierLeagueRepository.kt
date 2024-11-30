package com.example.thefootballshow.data.repository

import com.example.thefootballshow.data.Api.NetworkService
import com.example.thefootballshow.data.model.MatchInfo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class PremierLeagueRepository @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun getUpcomingMatches(year: Int, status: String) : Flow<List<MatchInfo>> {
        val upcomingMatches = networkService.getUpcomingMatchList(year, status)
        return flow {
            emit(upcomingMatches.matches)
        }
    }


}