package baeza.guillermo.uberroutes.register.domain.entity

data class UserModel(
    val id: String,
    val name: String,
    val surname: String,
    val password: String,
    val email: String
)