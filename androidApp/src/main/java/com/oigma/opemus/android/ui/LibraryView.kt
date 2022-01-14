package com.oigma.opemus.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oigma.opemus.android.AppTheme
import com.oigma.opemus.data.LibraryItem
import com.oigma.opemus.data.TrackManager

/**
 * Created by Harsewak Singh on 09/01/2022.
 */
@Composable
fun LibraryView(trackManager: TrackManager) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                com.oigma.opemus.android.TopAppBar(
                    contentPadding = AppBarDefaults.ContentPadding,
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.Menu, "menu")
                        }
                    },
                    title = {
                        Text(
//                            text = stringResource(R.string.app_name),
                            text = "Opemus",
                            color = MaterialTheme.colors.onPrimary,
                        )
                    },
                    actions = {

                    },
                )
            },
            bottomBar = {
                Spacer(
                    Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth()
                )
            },
        ) { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                LibraryListView(
                    trackManager.libraryItems.collectAsState(listOf()).value
                )
            }
        }
    }
}

@Composable
fun LibraryListView(items: List<LibraryItem>) {
    LazyColumn {
        items(items.size) { index ->
            LibraryItemView(items[index])
        }
    }
}

@Composable
fun LibraryItemView(item: LibraryItem) {
    Text(item.name.name, modifier = Modifier.defaultMinSize())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        LibraryListView(
            items = LibraryItem.previewItems
        )
    }
}