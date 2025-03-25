package cz.prukes.moneta.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cz.prukes.moneta.data.model.ApiResponse
import cz.prukes.moneta.data.model.Player
import cz.prukes.moneta.data.model.Team
import cz.prukes.moneta.data.paging.PlayersPagingSource
import cz.prukes.moneta.data.service.BallApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PlayerRepository @Inject constructor(private val apiService: BallApiService){

    suspend fun getTeam(teamId: Int):Response<ApiResponse<Team>>{
        return apiService.getTeamById(teamId);
    }

    suspend fun getPlayer(playerId: Int):Response<ApiResponse<Player>>{
        return apiService.getPlayerById(playerId);
    }

    fun getPlayersPagingFlow(pageSize: Int = 35): Flow<PagingData<Player>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PlayersPagingSource(apiService)
            }
        ).flow
    }
}