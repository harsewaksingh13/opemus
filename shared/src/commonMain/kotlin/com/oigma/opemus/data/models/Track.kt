package com.oigma.opemus.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Harsewak Singh on 08/01/2022.
 */
data class Track(
    val id: String,
    val name: String,
    val duration: Int,
    val size: Int,
    var state: TrackState = TrackState()
)

data class TrackState(var state: Int = none) {
    companion object {
        const val none: Int = -1
        const val playing: Int = 1
        const val paused: Int = 2
        const val stopped: Int = 0
    }
}

@Serializable
data class Response<T>(

    @SerialName("data")
    var data: T?,

)

@Serializable
data class TrackResponse(
    @SerialName("_id") var Id: String? = null,
    var name: String? = null,
    var artists: ArrayList<Artist> = arrayListOf(),
    var thumbnail: String? = null,
    var file: File? = File()
)

@Serializable
data class Artist(
    @SerialName("_id") var Id: String? = null,
    var name: String? = null,
    var type: String? = null
)

@Serializable
data class File(

    @SerialName("_id") var Id: String? = null,
    var url: String? = null,
    var extension: String? = null,
    var type: String? = null,
    var name: String? = null

)