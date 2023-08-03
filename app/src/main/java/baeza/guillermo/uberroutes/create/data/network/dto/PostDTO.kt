package baeza.guillermo.uberroutes.create.data.network.dto

import com.google.gson.annotations.SerializedName

data class PostDTO(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("distance") val distance: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("coordinates") val coordinates: MutableList<MutableList<Double>>,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("user") val user: String,
    @SerializedName("enterprise") val enterprise: String,
    @SerializedName("url") val url: String,
)
