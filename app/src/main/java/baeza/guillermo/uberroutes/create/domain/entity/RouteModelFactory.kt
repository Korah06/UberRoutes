package baeza.guillermo.uberroutes.create.domain.entity

import javax.inject.Inject

class RouteModelFactory @Inject constructor() {
    operator fun invoke(
        name: String,
        description: String,
        category: String,
        distance: String,
        difficulty: String,
        duration: String,
        coordinates: MutableList<MutableList<Double>>,
        privacy: String,
        enterprise: String,
        user: String,
        url: String
    ): RouteModel {
        return RouteModel(
            name = name,
            description = description,
            category = category,
            distance = distance,
            difficulty = difficulty,
            duration = duration,
            coordinates = coordinates,
            privacy = privacy,
            enterprise = enterprise,
            user = user,
            url = url
        )
    }
}
