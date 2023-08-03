package baeza.guillermo.uberroutes.profile.data.network.response

import baeza.guillermo.uberroutes.profile.data.network.dto.ProfileDTO
import com.google.gson.annotations.SerializedName

data class FollowResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: ProfileDTO
)
