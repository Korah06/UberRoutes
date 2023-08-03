package baeza.guillermo.uberroutes.home.data

import baeza.guillermo.uberroutes.home.data.network.HomeService
import baeza.guillermo.uberroutes.home.domain.entity.*
import baeza.guillermo.uberroutes.login.data.database.dao.UserDAO
import baeza.guillermo.uberroutes.home.domain.entity.UserModel
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeService,
    private val postModelFactory: PostModelFactory,
    private val commentModelFactory: CommentModelFactory,
    private val db: UserDAO,
    private val userModelFactory: UserModelFactory
){
    suspend fun getRoutes(): List<PostModel> {
        val posts = api.getPosts()
        val models: MutableList<PostModel> = mutableListOf()
        posts.forEach {
            if (it != null) {
                models.add(
                    postModelFactory(
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
}