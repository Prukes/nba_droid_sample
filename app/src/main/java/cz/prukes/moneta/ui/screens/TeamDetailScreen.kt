package cz.prukes.moneta.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.prukes.moneta.ui.components.CustomAppBar
import cz.prukes.moneta.ui.states.TeamDetailScreenState
import cz.prukes.moneta.ui.viewmodels.MainViewModel

@Composable
fun TeamDetailScreen(
    viewModel: MainViewModel,
    teamId: Int,
    onNavigateBack: () -> Unit
) {
    val teamState by viewModel.teamDetailState.collectAsStateWithLifecycle()

    // Load the team (only once) when this screen first appears
    LaunchedEffect(teamId) {
        viewModel.loadTeamDetails(teamId)
    }

    Scaffold(topBar = {
        CustomAppBar(
            title = "Team detail",
            onBackTap = onNavigateBack,
        )
    }) { insets ->


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(insets)
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            when (teamState) {
                is TeamDetailScreenState.Idle -> {
                    Text("Waiting to load team info...")
                }

                is TeamDetailScreenState.Loading -> {
                    CircularProgressIndicator()
                }

                is TeamDetailScreenState.Error -> {
                    Text("Error: ${(teamState as TeamDetailScreenState.Error).message}")
                }

                is TeamDetailScreenState.Success -> {
                    val team = (teamState as TeamDetailScreenState.Success).team
                    Text(text = "Team: ${team.fullName}")
                    Text(text = "City: ${team.city}")
                    Text(text = "Conference: ${team.conference}")
                    Text(text = "Division: ${team.division}")
                    Text(text = "Abbreviation: ${team.abbreviation}")
                }
            }
        }
    }
}
