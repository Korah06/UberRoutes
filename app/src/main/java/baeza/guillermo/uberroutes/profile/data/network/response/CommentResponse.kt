package baeza.guillermo.uberroutes.profile.data.network.response

import baeza.guillermo.uberroutes.profile.data.network.dto.CommentDTO
import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<CommentDTO>
)
