package com.oigma.opemus.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oigma.opemus.Greeting
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.oigma.opemus.data.LibraryItem
import com.oigma.opemus.data.TrackManager



fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : ComponentActivity() {
    private val trackManager: TrackManager by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // Logger.withTag("MainActivity").i("onCreate")

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                LibraryUI(trackManager)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity()
}