package com.oigma.opemus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.oigma.opemus.data.ResponseHandler


@Composable
fun Screen(content: @Composable () -> Unit) {
    Surface(modifier = Modifier.padding(top = Dp(28f)).fillMaxWidth().fillMaxHeight()) {
        Box(Modifier.padding(Dp(20f)).fillMaxWidth().fillMaxHeight()) {
            content()
        }
    }
}

typealias ClickHandler<T> = ResponseHandler<T>

@Composable
fun <T> ListView(
    items: List<T>,
    content: @Composable (T) -> Unit,
    clickHandler: ClickHandler<T>? = null
) {
    LazyColumn {
        items(items.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(Dp(4f))
                    .clickable { clickHandler?.invoke(items[index]) }) {
                content(items[index])
            }
        }
    }
}

@Composable
fun TopBar(
    title: String,
) {
    TopAppBar(
        title = { Text(title, color = MaterialTheme.colors.onPrimary) },
        backgroundColor = MaterialTheme.colors.primary
    )
}