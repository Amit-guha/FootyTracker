package com.example.thefootballshow.homescreen

import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.database.CompetitionEntity

fun CompetitionEntity.toAreaCompetition(): AreaCompetition {
    return AreaCompetition(
        id = id,
        name = name,
        code = code,
        type = type,
        emblem = emblem,
        isSelected = isSelected,
        plan = plan
    )
}

fun AreaCompetition.toCompetitionEntity(): CompetitionEntity {
    return CompetitionEntity(
        id = id,
        name = name,
        code = code,
        type = type,
        emblem = emblem,
        isSelected = isSelected,
        plan = plan
    )
}