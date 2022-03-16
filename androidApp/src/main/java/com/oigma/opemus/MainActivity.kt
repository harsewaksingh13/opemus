package com.oigma.opemus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oigma.opemus.data.TrackManager
import com.oigma.opemus.data.TrackManagerImpl
import com.oigma.opemus.data.executeTask
import com.oigma.opemus.data.models.Track
import com.oigma.opemus.data.services.ServiceManager
import com.oigma.opemus.theme.AppTheme
import com.oigma.opemus.ui.*
import kotlinx.coroutines.flow.MutableStateFlow


class MainActivity : ComponentActivity() {
    private lateinit var deviceDataManager: DeviceDataManager
    private val permissionManager = PermissionManager()
    private val trackManager: TrackManager =
        TrackManagerImpl(ServiceManager())
    private val songs: MutableStateFlow<List<Track>> =
        MutableStateFlow(listOf())
    private lateinit var player: Player


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceDataManager = DeviceDataManagerImpl(applicationContext)
        player = Player(applicationContext)

        lifecycle.addObserver(permissionManager)
        permissionManager.registry = activityResultRegistry
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            AppTheme {
                MainScreen(navController) {
                    NavHost(navController, startDestination = BottomNavigationScreen.Home.route) {
                        composable(BottomNavigationScreen.Home.route) {
                            SimpleScreen("Home")
                        }
                        composable(BottomNavigationScreen.Explore.route) {
                            SimpleScreen("Explore")
                        }
                        composable(BottomNavigationScreen.Library.route) {
                            LibraryView(
                                trackManager = trackManager,
                                navigationController = navController
                            )
                        }
                        composable("songs") {
                            SongsView(trackManager.recentTracks.collectAsState(arrayListOf()).value, player)
                        }
                        composable(BottomNavigationScreen.Upgrade.route) {
                            SimpleScreen("More")
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        trackManager.recentTracks.observe {
//            songs.value = it
//        }
//        songs.value = trackManager.recentTracks.mutable.value
        trackManager.fetchTracks()
        permissionManager.requestStoragePermissions {
            executeTask({
                deviceDataManager.findTracks()
            }, {
                songs.value = it
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity()
}