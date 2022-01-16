package com.oigma.opemus

import android.app.Activity
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.oigma.opemus.data.ErrorHandler
import com.oigma.opemus.data.ResponseHandler

/**
 * Created by Harsewak Singh on 16/01/2022.
 */
actual class PermissionAccess {
    actual var state: String = PermissionAccess.request

    actual companion object {
        actual val granted: String = "granted"
        actual val denied: String = "denied"
        actual val request: String = "request"
        actual val deniedFully: String = "denied_fully"
    }
}

actual class Permission {
    actual var name: String = readStorage
    actual var access: PermissionAccess = PermissionAccess()

    actual companion object {
        actual val readStorage: String = "android.permission.READ_EXTERNAL_STORAGE"
        actual val writeStorage: String = "android.permission.WRITE_EXTERNAL_STORAGE"

    }
}

actual class PermissionManager : DefaultLifecycleObserver {

    private lateinit var responseHandler: ResponseHandler<Permission>

    //should be set by activity/fragment
    var registry: ActivityResultRegistry? = null
    var owner: LifecycleOwner? = null

    actual fun requestStoragePermissions(
        responseHandler: ResponseHandler<Permission>,
        errorHandler: ErrorHandler
    ) {
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