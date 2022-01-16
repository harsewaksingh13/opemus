package com.oigma.opemus

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.oigma.opemus.data.FileManagerImpl
import com.oigma.opemus.data.TrackManager
import com.oigma.opemus.data.TrackManagerImpl
import com.oigma.opemus.data.services.ServiceManager
import com.oigma.opemus.theme.AppTheme
import com.oigma.opemus.ui.LibraryView


fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : ComponentActivity() {
    private val fileManager = FileManagerImpl(Environment.getExternalStorageDirectory().path)
    private val permissionManager = PermissionManager()
    private val trackManager: TrackManager =
        TrackManagerImpl(ServiceManager(), fileManager, permissionManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        trackManager.start()
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity()
}