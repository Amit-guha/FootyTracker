package com.example.thefootballshow.ui.upcomingMatchDetails

import android.health.connect.datatypes.SleepSessionRecord.StageType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.data.model.Standing
import com.example.thefootballshow.data.model.Standings
import com.example.thefootballshow.data.model.Table
import com.example.thefootballshow.data.model.Team
import com.example.thefootballshow.data.repository.UpcomingMatchDetailsRepository
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
class UpcomingMatchDetailsViewModel @Inject constructor(
    private val repository: UpcomingMatchDetailsRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var competitionId: Int = -1
    var homeTeamId: Int = -1
    var awayTeamId: Int = -1

    private val _preMatchDetailsInfo = MutableStateFlow<UiState<MatchInfo>>(UiState.Loading)
    val preMatchDetailsInfo: StateFlow<UiState<MatchInfo>> = _preMatchDetailsInfo

    private val _homeTeamMatchData = MutableStateFlow<UiState<List<MatchInfo>>>(UiState.Loading)
    val homeTeamMatchData: StateFlow<UiState<List<MatchInfo>>> = _homeTeamMatchData

    private val _awayTeamMatchData = MutableStateFlow<UiState<List<MatchInfo>>>(UiState.Loading)
    val awayTeamMatchData: StateFlow<UiState<List<MatchInfo>>> = _awayTeamMatchData

    private val _leagueTableInfo = MutableStateFlow<UiState<Standings>>(UiState.Loading)
    val leagueTableInfo: StateFlow<UiState<Standings>> = _leagueTableInfo


    fun getPreMatchDetailsInfo() {
        viewModelScope.launch(dispatcherProvider.main) {
            logger.d("competitionId2 :", "$competitionId")
            repository.getPreMatchDetails(competitionId = this@UpcomingMatchDetailsViewModel.competitionId)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _preMatchDetailsInfo.value = UiState.Error(e.message.toString())
                }
                .collect {
                    _preMatchDetailsInfo.value = UiState.Success(it)
                }
        }

    }


    fun getLastFiveMatchDetails() {
        viewModelScope.launch(dispatcherProvider.main) {
            showLog(message = homeTeamId.toString())
            repository.getLastFiveMatchInfo(homeTeamId)
                .flowOn(dispatcherProvider.io)
                .catch {
                    _homeTeamMatchData.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _homeTeamMatchData.value = UiState.Success(it)
                }
        }

        viewModelScope.launch(dispatcherProvider.main) {
            showLog(message = "awayTeam ->$awayTeamId")
            repository.getLastFiveMatchInfo(awayTeamId)
                .flowOn(dispatcherProvider.io)
                .catch {
                    _awayTeamMatchData.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _awayTeamMatchData.value = UiState.Success(it)
                }
        }

    }

    fun getStandingInfo() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getStanding(competitionId = "PL", season = 2024)
                .flowOn(dispatcherProvider.io)
                .catch {
                    _leagueTableInfo.value = UiState.Error(it.message.toString())
                }
                .collect {
                    val standings = it.standings.toMutableList()
                  /*  standings.add(0, Standing(
                        group = "",
                        type = "",
                        table = listOf(
                            Table(
                                draw = -1,
                                form = "",
                                goalDifference = -1,
                                goalsAgainst = -1,
                                goalsFor = -1,
                                lost = -1,
                                points = -1,
                                playedGames = -1,
                                position = -1,
                                won = -1,
                                team = Team(
                                    crest = "",
                                    name = "",
                                    shortName = "",
                                    id = -1,
                                    tla = ""
                                )
                            )
                        ),
                        stage = ""
                    ))*/
                    showLog(message = standings.joinToString { "" })
                   // _leagueTableInfo.value = UiState.Success(it.copy(standings = standings))
                    _leagueTableInfo.value = UiState.Success(it)
                }
        }
    }


    fun updateCompetitionId(competitionId: Int) {
        logger.d("competitionId1 :", "$competitionId")
        this.competitionId = competitionId
    }

    fun updateHomeTeamId(homeTeamId: Int) {
        this.homeTeamId = homeTeamId
    }

    fun updateAwayTeamId(awayTeamId: Int) {
        this.awayTeamId = awayTeamId
    }


}