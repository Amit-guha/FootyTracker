package com.example.thefootballshow.ui.profile

import com.example.thefootballshow.data.api.NetworkService
import com.example.thefootballshow.data.api.handleApiResponse
import com.example.thefootballshow.data.model.PlayerInfo
import com.example.thefootballshow.data.model.areaList._response.AreasResponse
import com.example.thefootballshow.ui.base.UiState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class PlayerProfileRepository @Inject constructor(
    private val networkService: NetworkService
){
    suspend fun getPlayerInfo(playerId : String) : Flow<UiState<PlayerInfo>>{
        val player = networkService.getPlayerPersonInfo(playerId)
        return flow {
            emit(handleApiResponse(player))
        }

    }

    suspend fun getAreaList() : Flow<UiState<AreasResponse>>{
        val areaList = networkService.getAreaList()
        return flow {
            emit(handleApiResponse(areaList))
        }

    }
    
}