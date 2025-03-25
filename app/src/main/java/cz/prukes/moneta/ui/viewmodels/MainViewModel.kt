package cz.prukes.moneta.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.prukes.moneta.data.model.ApiResponse
import cz.prukes.moneta.data.model.Player
import cz.prukes.moneta.data.model.Team
import cz.prukes.moneta.data.repository.PlayerRepository
import cz.prukes.moneta.ui.states.PlayerDetailScreenState
import cz.prukes.moneta.ui.states.TeamDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    // Cursor-based players flow
    val playerPagingFlow: Flow<PagingData<Player>> =
        repository.getPlayersPagingFlow().cachedIn(viewModelScope)

    // For player detail, if desired
    private val _playerDetailState =
        MutableStateFlow<PlayerDetailScreenState>(PlayerDetailScreenState.Idle)
    val playerDetailState: StateFlow<PlayerDetailScreenState> get() = _playerDetailState

    // For team detail, if desired
    private val _teamDetailState =
        MutableStateFlow<TeamDetailScreenState>(TeamDetailScreenState.Idle)
    val teamDetailState: StateFlow<TeamDetailScreenState> get() = _teamDetailState

    fun loadTeamDetails(teamId: Int) {
        _teamDetailState.value = TeamDetailScreenState.Loading
        viewModelScope.launch {
            try {
                val response: Response<ApiResponse<Team>> = repository.getTeam(teamId)
                if (response.isSuccessful) {
                    response.body()?.let { team ->
                        _teamDetailState.value = TeamDetailScreenState.Success(team.data)
                    } ?: run {
                        _teamDetailState.value = TeamDetailScreenState.Error("Team not found")
                    }
                } else {
                    _teamDetailState.value =
                        TeamDetailScreenState.Error("Request failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _teamDetailState.value = TeamDetailScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadPlayerDetails(playerId: Int) {
        _playerDetailState.value = PlayerDetailScreenState.Loading

        viewModelScope.launch {
            try {
                val response: Response<ApiResponse<Player>> = repository.getPlayer(playerId)
                if (response.isSuccessful) {
                    response.body()?.let { team ->
                        _playerDetailState.value = PlayerDetailScreenState.Success(team.data)
                    } ?: run {
                        _playerDetailState.value = PlayerDetailScreenState.Error("Player not found")
                    }
                } else {
                    _playerDetailState.value =
                        PlayerDetailScreenState.Error("Request failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _playerDetailState.value =
                    PlayerDetailScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setFoundPlayer(player: Player){
        _playerDetailState.value = PlayerDetailScreenState.Success(player = player)
    }

    fun resetTeamDetailState() {
        _teamDetailState.value = TeamDetailScreenState.Idle
    }
}