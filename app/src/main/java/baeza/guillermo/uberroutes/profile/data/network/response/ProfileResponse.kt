package baeza.guillermo.uberroutes.profile.data.network.response

import baeza.guillermo.uberroutes.profile.data.network.dto.PostsDTO
import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<PostsDTO>
)