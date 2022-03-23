package com.oigma.opemus.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Harsewak Singh on 12/01/2022.
 */
class AppError : Error {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}


typealias ResponseHandler<T> = (T) -> Unit
typealias DefaultResponseHandler = () -> Unit
typealias ErrorHandler = (AppError) -> Unit
typealias Task = Job

fun <T> Flow<T>.flowable(): Flowable<T> = Flowable(this)

class Flowable<T>(val origin: Flow<T>) : Flow<T> by origin {

    //called in swift to observe flow data
    fun observe(responseHandler: ResponseHandler<T>): RequestHandler {
        val job = Job()
        val requestHandler = RequestHandler(job)
        val errorHandler: ErrorHandler = { throwable ->
            throwable.printStackTrace()
            requestHandler.errorHandler?.let { it(throwable) }
        }
        onEach {
            runCatching {
                responseHandler(it)
            }.onFailure { errorHandler(AppError(it)) }
        }.launchIn(CoroutineScope(job + Dispatchers.Main))
        return requestHandler
    }
}


class RequestHandler(private val task: Task) {

    var errorHandler: ErrorHandler? = null

    fun onError(errorHandler: ErrorHandler) {
        this.errorHandler = errorHandler
    }

    fun cancel(cause: String? = null) {
        cause?.let {
            //task.cancel(CancellationException(cause))
        } ?: run { task.cancel() }
    }
}