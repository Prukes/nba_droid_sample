package cz.prukes.moneta.data.service

import cz.prukes.moneta.data.model.ApiResponse
import cz.prukes.moneta.data.model.Player
import cz.prukes.moneta.data.model.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BallApiService {
    // GET /players?page=1&per_page=20
    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int? = null,
        @Query("per_page") perPage: Int = 35
    ): Response<ApiResponse<List<Player>>>

    // GET /players/{id}
    @GET("players/{id}")
    suspend fun getPlayerById(
        @Path("id") playerId: Int
    ): Response<ApiResponse<Player>>

    // GET /teams/{id}
    @GET("teams/{id}")
    suspend fun getTeamById(@Path("id") teamId: Int): Response<ApiResponse<Team>>
}