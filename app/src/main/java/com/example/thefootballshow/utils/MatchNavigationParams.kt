package com.example.thefootballshow.utils

import com.example.thefootballshow.utils.enumUtills.MatchTypeEnum

data class MatchNavigationParams(
    val competitionId: Int?,
    val homeTeamId: Int?,
    val awayTeamId: Int?,
    val matchEnum : MatchTypeEnum
)
