package com.oigma.opemus

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.models.TrackState
import kotlinx.coroutines.flow.MutableStateFlow


/**
 * Created by Harsewak Singh on 16/01/2022.
 */
class Player(private val applicationContext: Context) {

    lateinit var track: MutableStateFlow<Track>

    private val mediaPlayer: MediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        //setDataSource(applicationContext, track.fd)
    }

    fun play(track: Track) {
        this.track = MutableStateFlow(track)
        reset()
        mediaPlayer.setDataSource(track.fd)
        mediaPlayer.prepare()
        mediaPlayer.start()
        this.track.value.state = TrackState(TrackState.playing)
    }

    private fun reset() {
        mediaPlayer.reset()
        this.track.value.state = TrackState(TrackState.none)
    }

    fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            this.track.value.state = TrackState(TrackState.playing)
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            this.track.value.state = TrackState(TrackState.paused)
        }
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}