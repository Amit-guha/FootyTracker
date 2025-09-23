package com.example.thefootballshow.di.module

import android.content.Context
import androidx.room.Room
import com.example.thefootballshow.database.CompetitionDao
import com.example.thefootballshow.database.FootballDatabase
import com.example.thefootballshow.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): FootballDatabase {
        return Room.databaseBuilder(
            context,
            FootballDatabase::class.java,
            AppConstant.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCompetitionDao(database: FootballDatabase) : CompetitionDao = database.competitionDao()

}