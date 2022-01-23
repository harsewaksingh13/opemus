package com.oigma.opemus.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oigma.opemus.Player
import com.oigma.opemus.data.LibraryItem
import com.oigma.opemus.data.ResponseHandler
import com.oigma.opemus.data.TrackManager
import com.oigma.opemus.data.models.Track
import com.oigma.opemus.theme.AppTheme

/**
 * Created by Harsewak Singh on 19/01/2022.
 */

@Composable
fun SongsView(songs: List<Track>, player: Player) {
    Screen {
        ListView(items = songs, content = { track ->
            Text(track.name,  modifier = Modifier.height(IntrinsicSize.Min))
        }, clickHandler = {
            player.play(it)
        })
    }
}

@Composable
fun SongsListView(tracks: List<Track>, onClickHandler: ResponseHandler<Track>? = null) {
    LazyColumn {
        items(tracks.size) { index ->
            Row(modifier = Modifier.clickable { onClickHandler?.invoke(tracks[index])}) {
                Text(tracks[index].name + index,  modifier = Modifier.height(IntrinsicSize.Min))
            }
        }
    }
}

@Preview()
@Composable
fun DefaultSongsPreview() {
    AppTheme {
        SongsListView(
            arrayListOf(Track("","test",0,0))
        )
    }
}