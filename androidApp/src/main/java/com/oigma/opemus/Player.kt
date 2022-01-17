package com.oigma.opemus

import android.content.ContentUris
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import com.oigma.opemus.data.models.Track

/**
 * Created by Harsewak Singh on 16/01/2022.
 */
class Player {

    lateinit var mediaPlayer: MediaPlayer
    lateinit var applicationContext: Context

    fun play(track: Track) {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(applicationContext, track.uri)
        }
        mediaPlayer.start()
    }
}