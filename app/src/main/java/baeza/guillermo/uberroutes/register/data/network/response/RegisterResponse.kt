package baeza.guillermo.uberroutes.register.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("status") val status: String,
    @SerializedName("token") val token: String
)