package com.oigma.opemus.data

import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.services.Services
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription

/**
 * Created by Harsewak Singh on 08/01/2022.
 *
 * a track is representation of song/video media file
 */
interface TrackManager: BaseManager {
    val recentTracks: Flow<List<Track>>
    fun fetchTracks()
}


class TrackManagerImpl(private val services: Services): BasicManager(), TrackManager {

    override val recentTracks: Flow<List<Track>>
        get() = _tracks

    private var _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(listOf())

    override fun fetchTracks() {
        execute({
            services.tracks.recentlyPlayed()
        }, {
            _tracks.value = it
        })
    }
}

