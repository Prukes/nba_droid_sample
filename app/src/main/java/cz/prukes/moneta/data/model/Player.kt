package cz.prukes.moneta.data.model

import com.google.gson.annotations.SerializedName

data class Player(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val position: String,
    val height: String,
    val weight: String,
    @SerializedName("jersey_number") val jerseyNumber: String,
    val college: String,
    val country: String,
    @SerializedName("draft_year") val draftYear: Int,
    @SerializedName("draft_round") val draftRound: Int,
    @SerializedName("draft_number") val draftNumber: Int,
    val team: Team

)
