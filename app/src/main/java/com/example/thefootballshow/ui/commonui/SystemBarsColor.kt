package com.example.thefootballshow.ui.commonui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SystemBarColor(
    statusBarColor: Color = Color.White,
    darkIcons: Boolean = true
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            WindowInsetsControllerCompat(window, window.decorView)
                .isAppearanceLightStatusBars = darkIcons

        }
    }

}