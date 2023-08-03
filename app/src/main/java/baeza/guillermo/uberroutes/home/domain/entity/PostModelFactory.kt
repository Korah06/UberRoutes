package baeza.guillermo.uberroutes.home.domain.entity

import javax.inject.Inject

class PostModelFactory @Inject constructor() {
    operator fun invoke(
        _id: String,
        name: String,
        description: String,
        date: String,
        category: String,
        distance: String,
        difficulty: String,
        duration: String,
        image: String,
        enterprise: String,
        user: String,
        url: String
    ): PostModel {
        return PostModel(
            _id = _id,
            name = name,
            description = description,
            date = date,
            category = category,
            distance = distance,
            difficulty = difficulty,
            duration = duration,
            image = image,
            enterprise = enterprise,
            user = user,
            url = url
        )
    }
}