package baeza.guillermo.uberroutes.register.data.dto

import com.google.gson.annotations.SerializedName

data class RegisterDTO (
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String
)