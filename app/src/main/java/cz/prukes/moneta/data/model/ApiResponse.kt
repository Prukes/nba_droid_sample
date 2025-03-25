package cz.prukes.moneta.data.model

data class ApiResponse<T>(val data: T, val meta: Meta)
data class Meta(val next_cursor: Int?,
                    val per_page: Int)
