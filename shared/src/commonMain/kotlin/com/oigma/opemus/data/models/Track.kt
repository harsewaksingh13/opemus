package com.oigma.opemus.data.models

/**
 * Created by Harsewak Singh on 08/01/2022.
 */
data class Track(val id: String, val name: String, val duration: Int, val size: Int, var state: TrackState = TrackState())

data class TrackState(var state: Int = none) {
    companion object {
        const val none : Int = -1
        const val playing : Int = 1
        const val paused : Int = 2
        const val stopped : Int = 0
    }
}