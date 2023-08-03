package baeza.guillermo.uberroutes.profile.data.network.dto

import com.google.gson.annotations.SerializedName

data class PostCommentDTO(
    @SerializedName("description") val description: String,
    @SerializedName("user") val user: String,
    @SerializedName("post") val post: String
)
