package com.example.thefootballshow.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.ui.base.Resource
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.Logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val logger: Logger
) : ViewModel() {

    private val _topLeagues: MutableStateFlow<UiState<List<AreaCompetition>>> =
        MutableStateFlow(UiState.Loading)
    val topLeagues: StateFlow<UiState<List<AreaCompetition>>> = _topLeagues

    init {
        getTopLeagues()
    }

    fun getTopLeagues() {
        viewModelScope.launch {
            repository.getTopLeague()
                .flowOn(Dispatchers.IO)
                .onStart { _topLeagues.value = UiState.Loading }
                .catch {
                    _topLeagues.value = UiState.Error(it.message ?: "Unexpected error")
                }.collectLatest { result ->
                    logger.d("HomeViewModel", "Top leagues result: $result")
                    _topLeagues.value = when (result) {
                        is Resource.Success -> UiState.Success(result.data)
                        is Resource.Error -> UiState.Error(result.message)
                    }

                }
        }
    }


    fun selectedLeague(leagueId: Int) {
        _topLeagues.update { currentState ->
            if (currentState is UiState.Success) {
                val updatedList = currentState.data.map { league ->
                    league.copy(isSelected = league.id == leagueId)
                }
                UiState.Success(updatedList)
            } else currentState
        }

    }
}