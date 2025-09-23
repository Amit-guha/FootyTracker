package com.example.thefootballshow.homescreen

import com.example.thefootballshow.data.api.NetworkService
import com.example.thefootballshow.data.api.safeApiCall
import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.data.model.MatchInfo
import com.example.thefootballshow.database.CompetitionDao
import com.example.thefootballshow.ui.base.Resource
import com.example.thefootballshow.ui.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val dao: CompetitionDao
) : HomeRepository {
    override suspend fun getTopLeague(): Flow<Resource<List<AreaCompetition>>> {
       return flow {
            try {
                val localData = dao.getAllCompetitions()
                if (localData.isNotEmpty()) {
                    val areaCompetitions = localData.map { it.toAreaCompetition() }
                    emit(Resource.Success(areaCompetitions))
                }
                val response = networkService.getTopLeague()
                val allCompetitions = safeApiCall(response) { it.competitions }
                val codes = setOf("PL", "BL1", "SA", "PD", "FL1", "CL")
                when (allCompetitions) {
                    is Resource.Success -> {
                        val selected = allCompetitions.data
                            .filter { it.code in codes }
                            .map { it.toCompetitionEntity() }

                        dao.insertAll(selected)
                        emit(Resource.Success(selected.map { it.toAreaCompetition() }))
                    }

                    is Resource.Error -> {
                        emit(Resource.Error(allCompetitions.message))
                    }
                }

            } catch (exception: Exception) {
                emit(Resource.Error(exception.message ?: "Unknown error"))
            }
        }


    }

    override suspend fun getLiveMatches(): Flow<UiState<List<MatchInfo>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMatches(): Flow<UiState<List<MatchInfo>>> {
        TODO("Not yet implemented")
    }
}