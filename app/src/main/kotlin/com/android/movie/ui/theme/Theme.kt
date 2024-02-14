package com.android.movie.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    secondary = Orange,
    tertiary = Grey10,
    onTertiary = Red,
    onTertiaryContainer = Grey40,
    background = Black,
    onBackground = Grey,
    surface = Bunting,
    onSurface = Color.White,
    onSurfaceVariant = Blue
)

private val LightColorScheme = lightColorScheme(
    primary = Green,
    secondary = Orange,
    tertiary = Grey10,
    onTertiary = Red,
    onTertiaryContainer = Grey40,
    background = Color.White,
    onBackground = Color.Black,
    surface = Bunting,
    onSurface = Color.White,
    onSurfaceVariant = Blue
)

@Composable
fun MovieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = MovieShape,
        content = content
    )
}