package com.example.thefootballshow.ui.premierleaguescreenroute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefootballshow.data.model.Competitions
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.repository.PremierLeagueRepository
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.utils.DispatcherProvider
import com.example.thefootballshow.utils.Logger.Logger
import com.example.thefootballshow.utils.MatchStatus
import com.example.thefootballshow.utils.extension.showLog
import com.example.thefootballshow.utils.extension.toTodayAndTomorrow
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

    private val _matchUiState = MutableStateFlow<UiState<List<MatchInfo>>>(UiState.Loading)
    val matchUiState: StateFlow<UiState<List<MatchInfo>>> = _matchUiState

    private val _competitionList = MutableStateFlow<UiState<Competitions>>(UiState.Loading)
    val competitionList : StateFlow<UiState<Competitions>> = _competitionList

    private var leagueId : Int = 2021
    private var previousSelection : Boolean = false

    init {
        getUpcomingMatches()
        getAllCompetitionInfo()
    }

    fun getUpcomingMatches() {
        viewModelScope.launch(dispatcherProvider.main) {
            //2021 -->PL
            //2014 -->Laliga
            //2018 -->euro
            val date  = System.currentTimeMillis().toTodayAndTomorrow()
            val queryMap = mapOf(
                "dateFrom" to date.first,
                "dateTo" to "2025-03-08",
                "status" to MatchStatus.SCHEDULED.title,
                "season" to "2023"
            )
            repository.getUpcomingMatches(leagueId = leagueId, queryMap)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _matchUiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _matchUiState.value = UiState.Success(it)
                }
        }
    }

    fun getAllCompetitionInfo() {
        viewModelScope.launch(dispatcherProvider.main) {
            //2077, 2088, 2224, 2081, 2077, 2114, 2072, 2220
            val areaIdList = listOf(2055,2077, 2088, 2224, 2081, 2077, 2114, 2072, 2220)
            repository.getAllCompetition(areaIdList.joinToString(","))
                .flowOn(dispatcherProvider.io)
                .catch {
                    _competitionList.value = UiState.Error(it.toString() ?: "Unknown error")
                }
                .collect {
                    val uniqueCompetitionIds = mutableListOf(2021,2014,2002,2019,2015,2146,2157,2182,2154,2001,2078,2079,2078,2056)
                    showLog(message = it.competitions.size.toString())
                    val modifiedList = it.competitions.filter { uniqueId ->
                        uniqueId.id in uniqueCompetitionIds
                    }
                /*    modifiedList.takeIf { it.isNotEmpty() && !previousSelection }?.let{
                        modifiedList[0].isSelected = true
                    }*/
                    modifiedList.map { competitionList ->
                        competitionList.isSelected = competitionList.id == leagueId
                    }

                    _competitionList.value =
                        UiState.Success(Competitions(count = it.count, competitions = modifiedList.toMutableList()))
                }
        }

    }

    fun updateAreaCompetition(leagueId: Int) {
        this.leagueId = leagueId
        val count = (_competitionList.value as? UiState.Success)?.data?.count ?: 0
        val updatedList = (_competitionList.value as? UiState.Success)?.data?.competitions?.map {
            it.copy(isSelected = it.id == leagueId) // Select only the matching item, deselect others
        } ?: return
        _competitionList.value =
            UiState.Success(Competitions(count = count, competitions = updatedList.toMutableList()))
       // previousSelection = true
        getUpcomingMatches()

    }

}