package com.oigma.opemus

import com.oigma.opemus.data.ErrorHandler
import com.oigma.opemus.data.ResponseHandler

/**
 * Created by Harsewak Singh on 14/01/2022.
 */
expect class PermissionManager {

    /**
     * request permission if not given, if granted callback with success
     * */
    fun requestStoragePermissions(responseHandler: ResponseHandler<Permission>, errorHandler: ErrorHandler)

}

expect class PermissionAccess {
    var state: String

    companion object {
         val granted: String
         val denied: String
         val request: String
         val deniedFully: String
    }
}

expect class Permission {
     var name: String
     var access: PermissionAccess

     companion object {
          val readStorage: String
          val writeStorage: String
//          val camera: String
//          val location: String
     }
}