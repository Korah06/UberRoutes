package baeza.guillermo.uberroutes.create.domain.entity

data class RouteModel(
    val name: String,
    val description: String,
    val category: String,
    val distance: String,
    val difficulty: String,
    val duration: String,
    val coordinates: MutableList<MutableList<Double>>,
    val privacy: String,
    val enterprise: String,
    val user: String,
    val url: String
)
