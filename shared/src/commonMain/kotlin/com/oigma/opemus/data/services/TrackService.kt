package com.oigma.opemus.data.services

import com.oigma.opemus.data.models.Track

/**
 * Created by Harsewak Singh on 09/01/2022.
 */
interface TrackService {
   suspend fun recentlyPlayed() : List<Track>
}