package com.oigma.opemus

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.oigma.opemus.data.models.Track
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
        runCatching {
            mediaPlayer.setDataSource(track.fd)
        }.onFailure {
            mediaPlayer.setDataSource(track.id)
        }
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    private fun reset() {
        mediaPlayer.reset()
    }

    fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}