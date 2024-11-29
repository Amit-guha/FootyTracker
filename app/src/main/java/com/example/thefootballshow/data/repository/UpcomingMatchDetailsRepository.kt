package com.example.thefootballshow.data.repository

import com.example.thefootballshow.data.Api.NetworkService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UpcomingMatchDetailsRepository @Inject constructor(
    private val networkService: NetworkService
) {
}