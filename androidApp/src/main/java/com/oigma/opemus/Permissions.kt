package com.oigma.opemus

import android.app.Activity
import android.os.Build
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.oigma.opemus.data.ResponseHandler

/**
 * Created by Harsewak Singh on 16/01/2022.
 */
class PermissionAccess {
     var state: String = request

     companion object {
         const val granted: String = "granted"
         const val denied: String = "denied"
         const val request: String = "request"
         const val deniedFully: String = "denied_fully"
    }
}

 class Permission {
     var name: String = readStorage
     var access: PermissionAccess = PermissionAccess()

     companion object {
         const val readStorage: String = "android.permission.READ_EXTERNAL_STORAGE"
         const val writeStorage: String = "android.permission.WRITE_EXTERNAL_STORAGE"

    }
}

 class PermissionManager : DefaultLifecycleObserver {

    private lateinit var responseHandler: ResponseHandler<Permission>

    //should be set by activity/fragment
    var registry: ActivityResultRegistry? = null
    var owner: LifecycleOwner? = null

     fun requestStoragePermissions(
        responseHandler: ResponseHandler<Permission>) {
        this.responseHandler = responseHandler
        val storage = Permission()
        requestPermission(storage)
    }

    private fun requestPermission(permission: Permission) {
        owner?.let { owner ->
            registry?.register(
                permission.name,
                owner,
                ActivityResultContracts.RequestPermission()
            ) { granted ->
                onPermissionResponse(permission, granted)
            }?.launch(permission.name)
        }
    }

    private fun onPermissionResponse(permission: Permission, granted: Boolean) {
        val permissionAccess = PermissionAccess()
        when {
            granted -> {
                permissionAccess.state = PermissionAccess.granted
            }
            shouldShowRequestPermissionRationale(permission.name) -> {
                permissionAccess.state = PermissionAccess.deniedFully
            }
            else -> {
                permissionAccess.state = PermissionAccess.denied
            }
        }
        permission.access = permissionAccess
        responseHandler.invoke(permission)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        this.owner = owner
    }

    fun isPermissionGranted(permission: String): Boolean {
        (owner as? Activity)?.let {
            return ContextCompat.checkSelfPermission(it, permission) == 0
        }
        return false
    }

    private fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        (owner as? Activity)?.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.shouldShowRequestPermissionRationale(permission)
            } else {
                return false
            }
        }
        return false
    }
}