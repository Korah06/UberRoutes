package baeza.guillermo.uberroutes.profile.domain.entity

data class UserModel (
    val _id: String,
    val name: String,
    val surname: String,
    val email: String,
    val following: String,
    val followers: String,
    val picture: String,
    val register: String,
    val web: String,
    val admin: Boolean
)