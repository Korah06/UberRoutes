package baeza.guillermo.uberroutes.profile.data.network.dto

data class PostsDTO (
    val _id: String,
    val name: String,
    val description: String,
    val date: String,
    val category: String,
    val distance: String,
    val difficulty: String,
    val duration: String,
    val image: String,
    val enterprise: String,
    val user: String,
    val url: String
)