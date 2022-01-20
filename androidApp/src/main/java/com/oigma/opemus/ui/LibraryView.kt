package com.oigma.opemus.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.oigma.opemus.data.LibraryItem
import com.oigma.opemus.data.ResponseHandler
import com.oigma.opemus.data.TrackManager
import com.oigma.opemus.theme.AppTheme

/**
 * Created by Harsewak Singh on 09/01/2022.
 */
@Composable
fun LibraryView(trackManager: TrackManager, navigationController: NavHostController) {

    Surface(modifier = Modifier.padding()) {

        Box(Modifier.padding(Dp(30f))) {
            LibraryListView(
                trackManager.libraryItems.collectAsState(listOf()).value
            ) {
                navigationController.navigate("songs")
            }
        }
    }
}

@Composable
fun LibraryListView(items: List<LibraryItem>, itemClickListener: ResponseHandler<LibraryItem>? = null) {
    LazyColumn {
        items(items.size) { index ->
            Row(modifier = Modifier.clickable { itemClickListener?.invoke(items[index]) }) {
                LibraryItemView(items[index])
            }
        }
    }
}

@Composable
fun LibraryItemView(item: LibraryItem) {
    Text(item.name.name, modifier = Modifier.defaultMinSize())
}

@Preview()
@Composable
fun DefaultPreview() {
    AppTheme {
        LibraryListView(
            items = LibraryItem.previewItems
        )
    }
}