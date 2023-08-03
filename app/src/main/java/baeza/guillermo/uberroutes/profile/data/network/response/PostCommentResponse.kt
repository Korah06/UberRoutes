package baeza.guillermo.uberroutes.profile.data.network.response

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(
    @SerializedName("status") val status: String,
    @SerializedName("response") val data: String
)
