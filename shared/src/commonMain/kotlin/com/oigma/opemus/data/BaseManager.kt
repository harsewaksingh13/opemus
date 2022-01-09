package com.oigma.opemus.data

import com.oigma.opemus.data.services.ErrorHandler
import com.oigma.opemus.data.services.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Harsewak Singh on 09/01/2022.
 */
interface BaseManager {
    val error: Flow<Throwable?>
}

open class BasicManager: BaseManager {


    override val error: Flow<Throwable?>
        get() = errorStateFlow

    protected var errorStateFlow: MutableStateFlow<Throwable?> = MutableStateFlow(null)

    fun <T> execute(block : suspend () -> T, responseHandler: ResponseHandler<T>, errorHandler: ErrorHandler? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                val results = block()
                withContext(Dispatchers.Main) {
                    responseHandler(results)
                }
            }.onFailure { throwable ->
                withContext(Dispatchers.Main) {
                    onError(throwable)
                    errorHandler?.let { it(throwable) }
                }
            }
        }
    }

    protected fun onError(throwable: Throwable) {
        errorStateFlow.value = throwable
    }
}