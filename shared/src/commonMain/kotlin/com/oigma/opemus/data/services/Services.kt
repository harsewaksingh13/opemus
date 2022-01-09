package com.oigma.opemus.data.services

import com.oigma.opemus.data.models.Track
import kotlinx.coroutines.Job

/**
 * Created by Harsewak Singh on 08/01/2022.
 */
interface Services {
    val tracks: TrackService
    val auth: AuthService
}

typealias ResponseHandler<T> = (T) -> Unit

typealias ErrorHandler = (Throwable) -> Unit

class RequestHandler(private val job: Job) {
    fun cancel() {
        job.cancel()
    }
}
