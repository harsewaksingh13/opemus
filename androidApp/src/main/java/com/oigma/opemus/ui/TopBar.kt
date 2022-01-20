package com.oigma.opemus.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
) {
    TopAppBar(
        title = { Text(title, color = MaterialTheme.colors.onPrimary) }, backgroundColor = MaterialTheme.colors.primary
    )
}

private val AppBarHorizontalPadding = 4.dp

// Start inset for the title when there is a navigation icon provided
private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(56.dp - AppBarHorizontalPadding)
