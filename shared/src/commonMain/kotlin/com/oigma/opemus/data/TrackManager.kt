package com.oigma.opemus.data

import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.services.Services
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Harsewak Singh on 08/01/2022.
 *
 * a track is representation of song/video media file
 */
interface TrackManager: BaseManager {
    val recentTracks: Flow<List<Track>>
    val libraryItems: Flow<List<LibraryItem>>
    fun fetchTracks()
}

data class LibraryItem(val icon: String, val name: LibraryType) {
    companion object {
        val previewItems: List<LibraryItem> = arrayListOf(LibraryItem("", LibraryType.songs), LibraryItem("", LibraryType.playlist), LibraryItem("", LibraryType.playlist), LibraryItem("", LibraryType.playlist), LibraryItem("", LibraryType.playlist), LibraryItem("", LibraryType.playlist), LibraryItem("", LibraryType.playlist), LibraryItem("", LibraryType.songs))
    }
}

data class LibraryType(val name: String) {
    companion object {
        val playlist: LibraryType = LibraryType("Playlist")
        val albums: LibraryType = LibraryType("Albums")
        val songs: LibraryType = LibraryType("Songs")
    }
}

class TrackManagerImpl(private val services: Services): BasicManager(), TrackManager {

    override val recentTracks: Flow<List<Track>>
        get() = _tracks

    override val libraryItems: Flow<List<LibraryItem>>
        get() = _libraryItems

    private var _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(listOf())

    private val _libraryItems: MutableStateFlow<List<LibraryItem>> = MutableStateFlow(listOf(LibraryItem(icon = "", name = LibraryType.songs)))

    override fun fetchTracks() {
        execute({
            services.tracks.recentlyPlayed()
        }, {
            _tracks.value = it
        })
    }
}

