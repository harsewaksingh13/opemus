package com.oigma.opemus

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.oigma.opemus.data.models.Track

/**
 * Created by Harsewak Singh on 16/01/2022.
 */
class Player(private val applicationContext: Context) {

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

        mediaPlayer.setDataSource(track.fd)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }
}