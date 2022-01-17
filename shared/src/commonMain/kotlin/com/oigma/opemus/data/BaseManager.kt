package com.oigma.opemus.data

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

fun <T> executeTask(
    block: suspend () -> T,
    responseHandler: ResponseHandler<T>,
    errorHandler: ErrorHandler? = null
) {
    CoroutineScope(Dispatchers.Default).launch {
        runCatching {
            val results = block()
            withContext(Dispatchers.Main) {
                responseHandler(results)
            }
        }.onFailure { throwable ->
            withContext(Dispatchers.Main) {
                errorHandler?.let { it(AppError(throwable)) }
            }
        }
    }
}

open class BasicManager : BaseManager {


    override val error: Flow<Throwable?>
        get() = errorStateFlow

    open var errorStateFlow: MutableStateFlow<Throwable?> = MutableStateFlow(null)

    fun <T> execute(
        block: suspend () -> T,
        responseHandler: ResponseHandler<T>,
        errorHandler: ErrorHandler? = null
    ) {
        executeTask(block, responseHandler) {
            onError(it)
            errorHandler?.invoke(it)
        }
    }

    protected fun onError(throwable: Throwable) {
        errorStateFlow.value = throwable
    }
}