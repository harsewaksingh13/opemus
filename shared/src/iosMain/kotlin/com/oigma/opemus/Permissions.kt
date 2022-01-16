package com.oigma.opemus

import com.oigma.opemus.data.ErrorHandler
import com.oigma.opemus.data.ResponseHandler

/**
 * Created by Harsewak Singh on 16/01/2022.
 */
actual class PermissionAccess {
    actual var state: String = PermissionAccess.request

    actual companion object {
        actual val granted: String = "given"
        actual val denied: String = "denied"
        actual val request: String = "request"
        actual val deniedFully: String = "denied_fully"
    }
}

actual class Permission {
    actual var name: String = Permission.readStorage
    actual var access: PermissionAccess = PermissionAccess()

    actual companion object {
        actual val readStorage: String = "readStorage"
        actual val writeStorage: String = "writeStorage"
    }
}

actual class PermissionManager {

    actual fun requestStoragePermissions(
        responseHandler: ResponseHandler<Permission>,
        errorHandler: ErrorHandler
    ) {

    }
}