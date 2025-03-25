package cz.prukes.moneta.ui.states

import cz.prukes.moneta.data.model.Team

sealed class TeamDetailScreenState {
    data object Idle : TeamDetailScreenState()
    data object Loading : TeamDetailScreenState()
    data class Success(val team: Team) : TeamDetailScreenState()
    data class Error(val message: String) : TeamDetailScreenState()
}