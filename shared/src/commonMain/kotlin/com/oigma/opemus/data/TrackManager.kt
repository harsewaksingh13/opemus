package com.oigma.opemus.data

import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.services.Services
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Harsewak Singh on 08/01/2022.
 *
 * a track is representation of song/video media file
 */
interface TrackManager : BaseManager {
    val recentTracks: Flowable<List<Track>>
    val libraryItems: Flowable<List<LibraryItem>>
    val songs: Flowable<List<Track>>
    fun fetchTracks()
    fun start()
}

data class LibraryItem(val id: Int, val icon: String, val name: LibraryType) {
    companion object {
        val previewItems: List<LibraryItem> = arrayListOf(
            LibraryItem(0, "", LibraryType.songs),
            LibraryItem(1, "", LibraryType.playlist),
            LibraryItem(2, "", LibraryType.albums)
        )
    }

    val isSongs = name == LibraryType.songs
}

data class LibraryType(val name: String) {
    companion object {
        val playlist: LibraryType = LibraryType("Playlist")
        val albums: LibraryType = LibraryType("Albums")
        val songs: LibraryType = LibraryType("Songs")
    }
}

class TrackManagerImpl(
    private val services: Services) : BasicManager(), TrackManager {

    override val recentTracks: Flowable<List<Track>>
        get() = _tracks.flowable()

    override val libraryItems: Flowable<List<LibraryItem>>
        get() = _libraryItems.flowable()

    override val songs: Flowable<List<Track>>
        get() = _songs.flowable()

    private var _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(listOf())

    private val _libraryItems: MutableStateFlow<List<LibraryItem>> =
        MutableStateFlow(LibraryItem.previewItems)

    private val _songs: MutableStateFlow<List<Track>> =
        MutableStateFlow(listOf())

    override fun start() {

    }

    override fun fetchTracks() {
        execute({
            services.tracks.recentlyPlayed()
        }, {
            _tracks.value = it
        })
    }
}

