package com.oigma.opemus.data.services

import com.oigma.opemus.data.models.Response
import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.models.TrackResponse
import io.ktor.client.call.*


/**
 * Created by Harsewak Singh on 09/01/2022.
 */
interface TrackService {
    suspend fun recentlyPlayed(): List<Track>
}

class TrackServiceImpl() : TrackService {

    private val client = ApiClient()

    override suspend fun recentlyPlayed(): List<Track> {
        val tracksData: Response<List<TrackResponse>> = client.get("tracks").body()
        return tracksData.data?.map { t -> Track(t.file?.url ?: "", t.name ?: "", 10,10)  } ?: listOf()
    }
}