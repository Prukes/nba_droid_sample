package cz.prukes.moneta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import cz.prukes.moneta.ui.screens.LandingScreen
import cz.prukes.moneta.ui.screens.PlayerDetailScreen
import cz.prukes.moneta.ui.screens.TeamDetailScreen
import cz.prukes.moneta.ui.theme.MonetaNBATheme
import cz.prukes.moneta.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val vm: MainViewModel = hiltViewModel()

            MonetaNBATheme(dynamicColor = false) {
                NavHost(navController = navController, startDestination = "landing_page") {
                    composable("landing_page") {
                        LandingScreen(
                            viewModel = vm,
                            onPlayerClick = { playerId: Int ->
                                navController.navigate("player_detail/$playerId")
                            }
                        )
                    }

                    composable(
                        "player_detail/{playerId}",
                        arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val playerId = backStackEntry.arguments?.getInt("playerId") ?: -1

                        PlayerDetailScreen(
                            viewModel = vm,
                            playerId = playerId,
                            onNavigateBack = { navController.popBackStack() },
                            onTeamClicked = { teamId: Int ->
                                navController.navigate("team_detail/$teamId")
                            }
                        )
                    }

                    composable(
                        "team_detail/{teamId}",
                        arguments = listOf(navArgument("teamId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val teamId = backStackEntry.arguments?.getInt("teamId") ?: -1

                        TeamDetailScreen(
                            viewModel = vm,
                            teamId = teamId,
                            onNavigateBack = {
                                vm.resetTeamDetailState()
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

