package com.oigma.opemus.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Screen {
        ListView(items = trackManager.libraryItems.collectAsState(listOf()).value, {
            LibraryItemView(it)
        }, {
            navigationController.navigate("songs")
        })
//        LibraryListView(
//            trackManager.libraryItems.collectAsState(listOf()).value
//        ) {
//            navigationController.navigate("songs")
//        }
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
//        LibraryView(
//            items = LibraryItem.previewItems
//        )
    }
}