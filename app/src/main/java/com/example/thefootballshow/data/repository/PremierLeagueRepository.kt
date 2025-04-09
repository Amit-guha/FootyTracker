package com.example.thefootballshow.data.repository

import com.example.thefootballshow.data.api.NetworkService
import com.example.thefootballshow.data.api.handleApiResponse
import com.example.thefootballshow.data.model.Competitions
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.TopScorer
import com.example.thefootballshow.ui.base.UiState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class PremierLeagueRepository @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun getUpcomingMatches(
        leagueId: Int,
        queryMap: Map<String, String>
    ): Flow<UiState<List<MatchInfo>>> {
        /*  val upcomingMatches = networkService.getUpcomingMatchList(leagueId, queryMap)
          return flow {
              emit(upcomingMatches.matches)
          }*/


        val response = networkService.getUpcomingMatchList(leagueId, queryMap)
        return flow { emit(handleApiResponse(response) { it.matches }) }
    }

    suspend fun getAllCompetition(areas: String): Flow<Competitions> {
        val allCompetitionInfo = networkService.getAllCompetitions(areas)
        return flow {
            emit(allCompetitionInfo)
        }
    }

    suspend fun getTopScorers(leagueCode: String, season: Int): Flow<TopScorer> {
        val topScorers = networkService.getTopScorer(leagueCode, season)
        return flow {
            emit(topScorers)
        }
    }


}