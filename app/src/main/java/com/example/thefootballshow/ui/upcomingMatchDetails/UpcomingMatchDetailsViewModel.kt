package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.lifecycle.ViewModel
import com.example.thefootballshow.data.repository.UpcomingMatchDetailsRepository
import com.example.thefootballshow.utils.DispatcherProvider
import com.example.thefootballshow.utils.Logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMatchDetailsViewModel @Inject constructor(
    private val repository: UpcomingMatchDetailsRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

}