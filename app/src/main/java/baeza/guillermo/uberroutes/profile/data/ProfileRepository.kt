package baeza.guillermo.uberroutes.profile.data

import baeza.guillermo.uberroutes.login.data.database.dao.UserDAO
import baeza.guillermo.uberroutes.profile.data.network.ProfileService
import baeza.guillermo.uberroutes.profile.domain.entity.*
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileService,
    private val profilePostModelFactory: ProfilePostModelFactory,
    private val userModelFactory: UserModelFactory,
    private val db: UserDAO,
    private val commentModelFactory: CommentModelFactory
){
    suspend fun getRoutes(userId: String): List<ProfilePostModel> {
        val posts = api.getPosts(userId)
        val models: MutableList<ProfilePostModel> = mutableListOf()
        posts.forEach {
            if (it != null) {
                models.add(
                    profilePostModelFactory(
                        _id = it._id,
                        name = it.name,
                        description = it.description,
                        date = it.date,
                        category = it.category,
                        distance = it.distance,
                        difficulty = it.difficulty,
                        duration = it.duration,
                        image = it.image,
                        enterprise = it.enterprise,
                        user = it.user,
                        url = it.url
                    )
                )
            }
        }
        return models
    }

    suspend fun getUser(): UserModel {
        val users = db.getAllUsers()
        return userModelFactory(
            _id = users[users.size-1].id,
            name = users[users.size-1].name,
            surname = users[users.size-1].surname,
            email = users[users.size-1].email,
            following = users[users.size-1].following,
            followers = users[users.size-1].followers,
            picture = users[users.size-1].picture,
            register = users[users.size-1].register,
            web = users[users.size-1].web,
            admin = users[users.size-1].admin
        )
    }

    suspend fun getFollowers(id: String): List<String> {
        return api.getFollowers(id)
    }

    suspend fun getFollowing(id: String): List<String> {
        return api.getFollowing(id)
    }

    suspend fun getComments(id: String): List<CommentModel> {
        val comments = api.getComments(id)
        val models: MutableList<CommentModel> = mutableListOf()
        comments.forEach {
            if (it != null) {
                models.add(
                    commentModelFactory(
                        date = it.date,
                        time = it.time,
                        description = it.description,
                        user = it.user,
                        post = it.post
                    )
                )
            }
        }
        return models
    }

    suspend fun postComment(description: String, userId: String, postId: String): Boolean {
        return api.postComment(description, userId, postId)
    }
}