package com.oigma.opemus

import io.ktor.client.*
import io.ktor.client.engine.ios.*
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Ios) {
    config(this)

    engine {
        configureRequest {
//            setAllowsCellularAccess(true)
        }
    }
}