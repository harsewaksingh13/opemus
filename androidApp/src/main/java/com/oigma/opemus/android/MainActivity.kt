package com.oigma.opemus.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.oigma.opemus.Greeting
import com.oigma.opemus.android.ui.LibraryView
import com.oigma.opemus.data.TrackManager
import com.oigma.opemus.data.TrackManagerImpl
import com.oigma.opemus.data.services.ServiceManager


fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : ComponentActivity() {
    private val trackManager: TrackManager = TrackManagerImpl(ServiceManager())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Logger.withTag("MainActivity").i("onCreate")

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                LibraryView(trackManager)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity()
}