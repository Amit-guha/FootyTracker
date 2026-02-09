package com.example.thefootballshow.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefootballshow.data.model.Scorer
import com.example.thefootballshow.data.model.areaList._response.AreaInfo
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.DispatcherProvider
import com.example.thefootballshow.utils.Logger.Logger
import com.example.thefootballshow.utils.extension.showLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerProfileViewModel @Inject constructor(
    private val playerProfileRepository: PlayerProfileRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(){

    private val _playerProfileInfo = MutableStateFlow<UiState<Scorer>>(UiState.Loading)
    val playerProfileInfo: StateFlow<UiState<Scorer>> = _playerProfileInfo

    private val _area = MutableStateFlow<UiState<AreaInfo>>(UiState.Loading)
    val area: StateFlow<UiState<AreaInfo>> = _area


    suspend fun getPlayerProfile(playerId : String){
        playerProfileRepository.getPlayerInfo(playerId = "")
            .flowOn(dispatcherProvider.io)
            .catch { e ->
                _playerProfileInfo.value = UiState.Error(e.toString())
            }
            .collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        _playerProfileInfo.value = UiState.Loading
                    }

                    is UiState.Success -> {
                        val player = uiState.data
                     //   _playerProfileInfo.value = UiState.Success(player)
                    }

                    is UiState.Error -> {
                        _playerProfileInfo.value = UiState.Error(uiState.message)
                    }
                }
            }

    }

     fun getAreaList(){
        viewModelScope.launch {
            playerProfileRepository.getAreaList()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _playerProfileInfo.value = UiState.Error(e.toString())
                }
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            _area.value = UiState.Loading
                        }

                        is UiState.Success -> {
                            val areaList = uiState.data
                            val scorerState = playerProfileInfo.value as? UiState.Success ?: return@collect
                            val scorer = scorerState.data

                            val filteredData = areaList.areas.firstOrNull {
                                it.name.equals(scorer.player?.nationality, ignoreCase = true)
                            }
                            showLog(tag = "Scorer ViewModel ->", message = filteredData.toString())
                            filteredData?.let {
                                _area.value = UiState.Success(filteredData)
                            }

                        }

                        is UiState.Error -> {
                            _area.value = UiState.Error(uiState.message)
                        }
                    }
                }
        }

    }

    fun initWithPlayerInfo(scorer: Scorer) {
       if (scorer.player == null){
           _playerProfileInfo.value = UiState.Error("No Player Found")
       }else{
           _playerProfileInfo.value = UiState.Success(data = scorer)
           getAreaList()
       }
    }
}