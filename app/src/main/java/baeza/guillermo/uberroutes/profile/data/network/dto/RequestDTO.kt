package baeza.guillermo.uberroutes.profile.data.network.dto

import com.google.gson.annotations.SerializedName

data class RequestDTO (
    @SerializedName("user") val user: String,
)