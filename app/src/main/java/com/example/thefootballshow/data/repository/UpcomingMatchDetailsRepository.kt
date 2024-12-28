package com.example.thefootballshow.data.repository

import android.util.Log
import com.example.thefootballshow.data.Api.NetworkService
import com.example.thefootballshow.data.model.MatchInfo
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
}