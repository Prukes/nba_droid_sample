package cz.prukes.moneta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import cz.prukes.moneta.data.model.Player
import cz.prukes.moneta.ui.components.CustomAppBar
import cz.prukes.moneta.ui.viewmodels.MainViewModel

@Composable
fun LandingScreen(
    viewModel: MainViewModel,
    onPlayerClick: (Int) -> Unit
) {
    val lazyPagingItems = viewModel.playerPagingFlow.collectAsLazyPagingItems()

    val loadState = lazyPagingItems.loadState
    val isLoading = loadState.refresh is LoadState.Loading

    if (loadState.hasError) {
        return Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Text(loadState.refresh.toString()) }
    }

    Scaffold(topBar = {
        CustomAppBar(
            title = "NBA players",
            onBackTap = {},
            showBackButton = false
        )
    }) { insets ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets)
                .padding(12.dp, 8.dp, 12.dp, 0.dp)
        ) {
            if (isLoading && lazyPagingItems.itemCount == 0) {
                Box {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(lazyPagingItems.itemCount) { index ->
                        val player = lazyPagingItems[index]
                        if (player != null) {
                            Box(
                                modifier = Modifier
                                    .aspectRatio(0.7f)
                                    .shadow(
                                        elevation = 1.dp,
                                        shape = RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp),
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Black,
                                        shape = RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp))
                            ) {
                                PlayerListItem(player) {
                                    onPlayerClick(player.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerListItem(
    player: Player,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    listOf(
                        Color(0xFF1D428A),
                        Color(0xFF2B61CB)
                    )
                )
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        // Use GlideImage to load a placeholder
//        GlideImage(
//            model = R.drawable.single_cartoon_basketball_ball_back_to_school_vector,
//            modifier = Modifier.size(14.dp),
//            contentDescription = "Placeholder"
//        )

//        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "${player.firstName} ${player.lastName}",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Position: ${player.position}",
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Team: ${player.team.fullName}",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
//        Spacer(modifier = Modifier.width(16.dp))
    }
}