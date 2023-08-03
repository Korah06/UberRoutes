package baeza.guillermo.uberroutes.profile.domain.entity

import javax.inject.Inject

class ProfilePostModelFactory @Inject constructor() {
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
    ): ProfilePostModel {
        return ProfilePostModel(
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