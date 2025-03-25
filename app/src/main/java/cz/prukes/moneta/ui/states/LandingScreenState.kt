package cz.prukes.moneta.ui.states

import cz.prukes.moneta.data.model.Player

sealed class LandingScreenState {
    data object Loading : LandingScreenState()
    data class Success(val players: List<Player>, val canLoadMore: Boolean) : LandingScreenState()
    data class Error(val message: String) : LandingScreenState()
}