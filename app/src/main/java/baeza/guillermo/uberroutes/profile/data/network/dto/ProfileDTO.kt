package baeza.guillermo.uberroutes.profile.data.network.dto

data class ProfileDTO(
    val _id: String,
    val name: String,
    val surname: String,
    val email: String,
    val following: List<String>,
    val followers: List<String>,
    val picture: String,
    val register: String,
    val web: String,
    val admin: Boolean
)
