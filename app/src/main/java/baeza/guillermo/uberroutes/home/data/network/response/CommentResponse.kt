package baeza.guillermo.uberroutes.home.data.network.response

import baeza.guillermo.uberroutes.home.data.network.dto.CommentDTO
import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<CommentDTO>
)
