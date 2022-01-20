package com.oigma.opemus.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import com.oigma.opemus.*
import com.oigma.opemus.android.Shapes

/**
 * Created by Harsewak Singh on 09/01/2022.
 */

private val DarkColorPalette = darkColors(
    primary = Color.DarkGray,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Color.LightGray
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Color.Black
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
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