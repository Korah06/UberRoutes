package baeza.guillermo.uberroutes.profile.domain.entity

import javax.inject.Inject

class CommentModelFactory @Inject constructor() {
    operator fun invoke(
        date: String,
        time: String,
        description: String,
        user: String,
        post: String
    ): CommentModel {
        return CommentModel(
            date = date,
            time = time,
            description = description,
            user = user,
            post = post
        )
    }
}