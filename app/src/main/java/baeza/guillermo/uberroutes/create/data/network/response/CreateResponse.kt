package baeza.guillermo.uberroutes.create.data.network.response

import com.google.gson.annotations.SerializedName

data class CreateResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)
