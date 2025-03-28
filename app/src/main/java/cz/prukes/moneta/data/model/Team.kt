package cz.prukes.moneta.data.model

import com.google.gson.annotations.SerializedName

data class Team( val id: Int,
                 val abbreviation: String,
                 val city: String,
                 val conference: String,
                 val division: String,
                 @SerializedName("full_name") val fullName: String,
                 val name: String)
