package baeza.guillermo.uberroutes.login.data.network.dto

import com.google.gson.annotations.SerializedName

data class LoginDTO(
    @SerializedName("_id") val id: String,
    @SerializedName("password") val password: String,
)