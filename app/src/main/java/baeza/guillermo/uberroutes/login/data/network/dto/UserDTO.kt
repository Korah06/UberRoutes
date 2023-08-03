package baeza.guillermo.uberroutes.login.data.network.dto

data class UserDTO(
    val _id: String,
    val name: String,
    val surname: String,
    val email: String,
    val following: List<String>,
    val followers: List<String>,
    val picture: String,
    val register: String,
    val web: String,
    val admin: Boolean,
    val token: String
)
