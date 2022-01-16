package com.oigma.opemus.data.services

import kotlinx.coroutines.Job

/**
 * Created by Harsewak Singh on 08/01/2022.
 */
interface Services {
    val tracks: TrackService
    val auth: AuthService
}


class ServiceManager : Services {
    override val tracks: TrackService
        get() = TrackServiceImpl()
    override val auth: AuthService
        get() = AuthServiceImpl()

}
