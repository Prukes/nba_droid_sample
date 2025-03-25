package cz.prukes.moneta.ui.states

import cz.prukes.moneta.data.model.Player

sealed class PlayerDetailScreenState {
    data object Idle : PlayerDetailScreenState()
    data object Loading : PlayerDetailScreenState()
    data class Success(val player: Player?) : PlayerDetailScreenState()
    data class Error(val message: String) : PlayerDetailScreenState()
}