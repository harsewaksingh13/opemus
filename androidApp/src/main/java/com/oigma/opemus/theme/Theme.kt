package com.oigma.opemus.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.oigma.opemus.*
import com.oigma.opemus.android.Shapes

/**
 * Created by Harsewak Singh on 09/01/2022.
 */

private val DarkColorPalette = darkColors(
    primary = Color.DarkGray,
    primaryVariant = Color.DarkGray,
    secondary = Color.Black,
    onPrimary = Color.LightGray
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.Green,
    secondary = Color.Gray,
    onPrimary = Color.Black,
    background = Color.White,
    surface = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}