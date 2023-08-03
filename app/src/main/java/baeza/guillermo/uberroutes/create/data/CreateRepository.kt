package baeza.guillermo.uberroutes.create.data

import baeza.guillermo.uberroutes.create.data.network.CreateService
import baeza.guillermo.uberroutes.create.data.network.dto.PostDTO
import baeza.guillermo.uberroutes.create.domain.entity.RouteModel
import baeza.guillermo.uberroutes.login.data.database.dao.UserDAO
import javax.inject.Inject

class CreateRepository @Inject constructor(
    private val db: UserDAO,
    private val api: CreateService
) {
    suspend fun getCurrentUserId(): String {
        val user = db.getAllUsers()
        return user[user.size-1].id
    }

    suspend fun postRoute(post: RouteModel): Boolean {
        return api.postRoute(
            post = PostDTO(
                name = post.name,
                description = post.description,
                category = post.category,
                distance = post.distance,
                difficulty = post.difficulty,
                duration = post.duration,
                coordinates = post.coordinates,
                privacy = post.privacy,
                enterprise = post.enterprise,
                user = post.user,
                url = post.url
            )
        )
    }
}