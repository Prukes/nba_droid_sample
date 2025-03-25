package cz.prukes.moneta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    onBackTap: () -> Unit,
    showBackButton: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val colorss = listOf(Color(0xFF1D428A),White, Color(0xFFFC8102E))
    CenterAlignedTopAppBar(
        modifier = Modifier.background(Brush.horizontalGradient(colorss)),
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            navigationIconContentColor = White,
            containerColor = Color.Transparent,
//            titleContentColor = MaterialTheme.colorScheme.onPrimary,
//            containerColor = Brush.horizontalGradient(colorStops = colorss)
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackTap) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"

                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}