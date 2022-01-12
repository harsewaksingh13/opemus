package com.oigma.opemus.data

import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.services.Services
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by Harsewak Singh on 08/01/2022.
 *
 * a track is representation of song/video media file
 */
interface TrackManager : BaseManager {
    val recentTracks: List<Track>
    val libraryItems: Flowable<List<LibraryItem>>
    fun fetchTracks()
}

data class LibraryItem(val id: Int, val icon: String, val name: LibraryType) {
    companion object {
        val previewItems: List<LibraryItem> = arrayListOf(
            LibraryItem(0, "", LibraryType.songs),
            LibraryItem(1, "", LibraryType.playlist),
            LibraryItem(2, "", LibraryType.albums),
            LibraryItem(3, "", LibraryType.playlist),
            LibraryItem(4, "", LibraryType.albums),
            LibraryItem(5, "", LibraryType.playlist),
            LibraryItem(6, "", LibraryType.albums),
            LibraryItem(7, "", LibraryType.songs)
        )
    }
}

data class LibraryType(val name: String) {
    companion object {
        val playlist: LibraryType = LibraryType("Playlist")
        val albums: LibraryType = LibraryType("Albums")
        val songs: LibraryType = LibraryType("Songs")
    }
}

class TrackManagerImpl(private val services: Services) : BasicManager(), TrackManager {

    override val recentTracks: List<Track>
        get() = _tracks.asStateFlow().value

    override val libraryItems: Flowable<List<LibraryItem>>
        get() = _libraryItems.flowable()

    private var _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(listOf())

    private val _libraryItems: MutableStateFlow<List<LibraryItem>> =
        MutableStateFlow(LibraryItem.previewItems)

    override fun fetchTracks() {
        execute({
            services.tracks.recentlyPlayed()
        }, {
            _tracks.value = it
        })
    }
}

