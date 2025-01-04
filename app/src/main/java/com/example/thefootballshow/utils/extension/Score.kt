package com.example.thefootballshow.utils.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.Score

@Composable
fun Score.getResultColor(): Color {
    return when {
        fullTime.home == fullTime.away -> colorResource(id = R.color.outer_space)
        fullTime.home > fullTime.away -> colorResource(id = R.color.emerald_green)
        else -> colorResource(id = R.color.pink_500)
    }
}