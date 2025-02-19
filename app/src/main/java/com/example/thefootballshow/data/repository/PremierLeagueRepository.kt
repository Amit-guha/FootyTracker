package com.example.thefootballshow.data.repository

import com.example.thefootballshow.data.Api.NetworkService
import com.example.thefootballshow.data.model.Competitions
import com.example.thefootballshow.data.model.MatchInfo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class PremierLeagueRepository @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun getUpcomingMatches(leagueId: Int, queryMap: Map<String, String>) : Flow<List<MatchInfo>> {
        val upcomingMatches = networkService.getUpcomingMatchList(leagueId, queryMap)
        return flow {
            emit(upcomingMatches.matches)
        }
    }

    suspend fun getAllCompetition(areas : String) : Flow<Competitions>{
        val allCompetitionInfo = networkService.getAllCompetitions(areas)
        return flow {
            emit(allCompetitionInfo)
        }
    }


}