package com.example.thefootballshow.data.repository

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
        val preMatchInfo = networkService.getPreMatchDetails(competitionId)
        return flow {
            emit(preMatchInfo)
        }
    }
}