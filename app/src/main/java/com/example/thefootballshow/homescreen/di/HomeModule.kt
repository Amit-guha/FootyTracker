package com.example.thefootballshow.homescreen.di

import com.example.thefootballshow.data.api.NetworkService
import com.example.thefootballshow.database.CompetitionDao
import com.example.thefootballshow.homescreen.HomeRepository
import com.example.thefootballshow.homescreen.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        networkService: NetworkService,
        dao: CompetitionDao
    ): HomeRepository {
        return HomeRepositoryImpl(networkService, dao)
    }

}