package com.oigma.opemus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.oigma.opemus.data.TrackManager
import com.oigma.opemus.data.TrackManagerImpl
import com.oigma.opemus.data.executeTask
import com.oigma.opemus.data.services.ServiceManager
import com.oigma.opemus.theme.AppTheme
import com.oigma.opemus.ui.LibraryView


class MainActivity : ComponentActivity() {
    private lateinit var fileManager: DeviceDataManager
    private val permissionManager = PermissionManager()
    private val trackManager: TrackManager =
        TrackManagerImpl(ServiceManager())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileManager = DeviceDataManagerImpl(applicationContext)
        // Logger.withTag("MainActivity").i("onCreate")
        lifecycle.addObserver(permissionManager)
        permissionManager.registry = activityResultRegistry
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                LibraryView(trackManager)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        permissionManager.requestStoragePermissions {
            executeTask({
                fileManager.findTracks()
            }, {
//                val items = mutableListOf<LibraryItem>()
//                items.addAll(_libraryItems.value)
//                items.addAll(tracks.map { LibraryItem(9, "", LibraryType(it.file?.name ?: "")) })

            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity()
}