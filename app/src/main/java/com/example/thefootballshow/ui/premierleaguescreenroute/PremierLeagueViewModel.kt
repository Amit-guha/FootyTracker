package com.example.thefootballshow.ui.premierleaguescreenroute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefootballshow.data.model.Matche
import com.example.thefootballshow.data.repository.PremierLeagueRepository
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.DispatcherProvider
import com.example.thefootballshow.utils.Logger.Logger
import com.example.thefootballshow.utils.MatchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PremierLeagueViewModel @Inject constructor(
    private val repository: PremierLeagueRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _matchUiState = MutableStateFlow<UiState<List<Matche>>>(UiState.Loading)
    val matchUiState: StateFlow<UiState<List<Matche>>> = _matchUiState

    fun getUpcomingMatches() {
        viewModelScope.launch(dispatcherProvider.main) {
            //2021 -->PL
            //2014 -->Laliga
            repository.getUpcomingMatches(year = 2014, status = MatchStatus.SCHEDULED.title)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _matchUiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _matchUiState.value = UiState.Success(it)
                }
        }
    }

}