package com.example.thefootballshow.data.repository

import android.util.Log
import com.example.thefootballshow.data.api.NetworkService
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.Standings
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class UpcomingMatchDetailsRepository @Inject constructor(
    private val networkService: NetworkService
) {
    suspend fun getPreMatchDetails(competitionId : Int): Flow<MatchInfo> {
        Log.d("competitionId3 :", "$competitionId")
        val preMatchInfo = networkService.getPreMatchDetails(competitionId)
        return flow {
            emit(preMatchInfo)
        }
    }


    suspend fun getLastFiveMatchInfo(teamId : Int): Flow<List<MatchInfo>> {
        val lastFiveMatchInfo = networkService.getLastFiveMatchInfo(teamId)
        return flow {
            emit(lastFiveMatchInfo.matches)
        }

    }

    suspend fun getStanding(competitionId : String, season : Int): Flow<Standings> {
        val standings = networkService.getStandings(competitionId = competitionId, season = season)
        return flow {
            emit(standings)
        }
    }
}