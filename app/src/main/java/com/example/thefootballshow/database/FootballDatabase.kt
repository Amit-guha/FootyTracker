package com.example.thefootballshow.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CompetitionEntity::class], version = 1, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun competitionDao(): CompetitionDao
}