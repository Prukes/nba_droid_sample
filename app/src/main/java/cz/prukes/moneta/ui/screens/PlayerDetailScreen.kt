package cz.prukes.moneta.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import cz.prukes.moneta.data.model.Player
import cz.prukes.moneta.ui.components.CustomAppBar
import cz.prukes.moneta.ui.states.PlayerDetailScreenState
import cz.prukes.moneta.ui.viewmodels.MainViewModel

@Composable
fun PlayerDetailScreen(
    viewModel: MainViewModel,
    playerId: Int,
    onNavigateBack: () -> Unit,
    onTeamClicked: (Int) -> Unit
) {

    val playerDetailScreenState by viewModel.playerDetailState.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.playerPagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(playerId) {
        //Kinda hacky approach, ngl (just to show the possibility)
        val itemCount = lazyPagingItems.itemCount
        val foundPlayer: Player? = run {
            var result: Player? = null
            for (index in 0 until itemCount) {
                val p = lazyPagingItems.peek(index)
                if (p?.id == playerId) {
                    result = p
                    break
                }
            }
            result
        }
        if (foundPlayer != null) {
            viewModel.setFoundPlayer(foundPlayer)
        } else {
            viewModel.loadPlayerDetails(playerId)
        }

    }


    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Player detail", onBackTap = onNavigateBack
            )
        }
    ) { insets ->
        when (playerDetailScreenState) {
            is PlayerDetailScreenState.Loading -> Box {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is PlayerDetailScreenState.Error -> Box() {
                Text(
                    (playerDetailScreenState as PlayerDetailScreenState.Error).message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is PlayerDetailScreenState.Idle -> Box {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is PlayerDetailScreenState.Success -> Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()
                    .padding(insets)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                val foundPlayer =
                    (playerDetailScreenState as PlayerDetailScreenState.Success).player
                if (foundPlayer != null) {
                    Text("Player: ${foundPlayer.firstName} ${foundPlayer.lastName}")
                    Text("Position: ${foundPlayer.position}")
                    Text("Height: ${foundPlayer.height}")
                    Text("Weight: ${foundPlayer.weight}")
                    Text("Jersey number: ${foundPlayer.jerseyNumber}")
                    Text("College: ${foundPlayer.college}")
                    Text("Country: ${foundPlayer.country}")
                    Text("Draft year: ${foundPlayer.draftYear}")
                    Text("Draft round: ${foundPlayer.draftRound}")
                    Text("Draft number: ${foundPlayer.draftNumber}")
                    Text("Team: ${foundPlayer.team.fullName}")

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Go to Team Details",
                        modifier = Modifier.clickable {
                            onTeamClicked(foundPlayer.team.id)
                        }
                    )
                } else {
                    Text("Player not found in the currently loaded pages.")
                }
            }
        }

    }
}