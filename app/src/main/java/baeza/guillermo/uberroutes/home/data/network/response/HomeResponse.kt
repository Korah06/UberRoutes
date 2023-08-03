package baeza.guillermo.uberroutes.home.data.network.response

import baeza.guillermo.uberroutes.home.data.network.dto.HomeDTO
import com.google.gson.annotations.SerializedName

data class HomeResponse (
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<HomeDTO>
)