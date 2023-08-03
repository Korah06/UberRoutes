package baeza.guillermo.uberroutes.login.data.network.response

import baeza.guillermo.uberroutes.login.data.network.dto.UserDTO
import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("status") val status: String,
                         @SerializedName("token") val token: String,
                         @SerializedName("data") val data: UserDTO)