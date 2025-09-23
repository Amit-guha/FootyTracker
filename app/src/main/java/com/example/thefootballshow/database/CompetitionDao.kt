package com.example.thefootballshow.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thefootballshow.utils.AppConstant


@Dao
interface CompetitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(competitions : List<CompetitionEntity>)

    @Query("SELECT * FROM ${AppConstant.COMPETITION_TABLE}")
    suspend fun getAllCompetitions() : List<CompetitionEntity>
}