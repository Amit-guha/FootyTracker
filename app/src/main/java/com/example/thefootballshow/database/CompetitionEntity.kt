package com.example.thefootballshow.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thefootballshow.utils.AppConstant

@Entity(tableName = AppConstant.COMPETITION_TABLE)
data class CompetitionEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val code: String?,
    val type: String?,
    val emblem: String?,
    val plan: String?,
    var isSelected: Boolean = false
)
