package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.repository.UpcomingMatchDetailsRepository
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.DispatcherProvider
import com.example.thefootballshow.utils.Logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMatchDetailsViewModel @Inject constructor(
    private val repository: UpcomingMatchDetailsRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _preMatchDetailsInfo  = MutableStateFlow<UiState<MatchInfo>>(UiState.Loading)
    val preMatchDetailsInfo : StateFlow<UiState<MatchInfo>> = _preMatchDetailsInfo

    fun getPreMatchDetailsInfo(){
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getPreMatchDetails(competitionId = 497538)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _preMatchDetailsInfo.value = UiState.Error(e.message.toString())
                }
                .collect{
                    _preMatchDetailsInfo.value = UiState.Success(it)
                }
        }

    }

}