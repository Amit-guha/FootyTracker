package com.example.thefootballshow.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppCustomColors(
    val winPrimary: Color,
    val drawPrimary: Color,
    val teamNameColor : Color,
    val negativeMomentumColor: Color,
    val drawProgressBar : Color,
    val awayTeamProgressBar : Color
)


val CustomLightAppColorScheme = AppCustomColors(
    winPrimary = White,
    drawPrimary = OuterSpace,
    teamNameColor = OuterSpace,
    negativeMomentumColor = coralOrange60,
    drawProgressBar = GrayGreen,
    awayTeamProgressBar = SoftGrayGreen
)

val CustomDarkAppColorScheme = AppCustomColors(
    winPrimary = DarkJungleGreen,
    drawPrimary = Silver,
    teamNameColor = White50,
    negativeMomentumColor = Coral,
    drawProgressBar = Silver,
    awayTeamProgressBar = White20
)
// ─────────────────────────────────────────────
//  4.  CompositionLocal
// ─────────────────────────────────────────────

/**
 * CompositionLocal that holds the current [AppColorScheme].
 *
 * Access anywhere in the Compose tree via [LocalAppColorScheme.current].
 * Throws at runtime if no provider is found (use [staticCompositionLocalOf]
 * so the error is clear and immediate).
 */
val LocalAppColorScheme = staticCompositionLocalOf<AppCustomColors> {
    error(
        "No AppColorScheme provided. " +
                "Did you forget to wrap your content in MyAppTheme { }?"
    )
}

object AppTheme {
    val customColors: AppCustomColors
        @Composable
        get() = LocalAppColorScheme.current
}


