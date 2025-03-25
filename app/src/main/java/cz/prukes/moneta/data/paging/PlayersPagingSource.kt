package cz.prukes.moneta.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.prukes.moneta.data.model.Player
import cz.prukes.moneta.data.service.BallApiService

class PlayersPagingSource(
    private val apiService: BallApiService
) : PagingSource<Int, Player>() {
    private val MAX_LOAD_SIZE = 35

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        return try {
            // 'cursor' is the current key (null => start at beginning).
            val currentCursor = params.key  // Could be null on first load.
            // loadSize typically matches pageSize from PagingConfig.
            val perPage = params.loadSize.coerceAtMost(MAX_LOAD_SIZE)

            // Make the network request
            val response = apiService.getPlayers(cursor = currentCursor, perPage = perPage)
            if (response.isSuccessful) {
                val body = response.body()
                val players = body?.data.orEmpty()
                // If next_cursor == null => no more data
                val nextCursor = body?.meta?.next_cursor

                LoadResult.Page(
                    data = players,
                    prevKey = null,        // Usually no "previous" in forward-only cursor
                    nextKey = nextCursor   // next_cursor becomes the nextKey
                )
            } else {
                LoadResult.Error(Exception("HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // Usually safe to return null or do advanced anchor logic
    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        // The simplest approach: no explicit refresh key for a forward-only cursor
        return null
    }
}