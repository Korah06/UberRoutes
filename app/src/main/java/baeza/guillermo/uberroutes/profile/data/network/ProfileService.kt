package baeza.guillermo.uberroutes.profile.data.network

import android.util.Log
import baeza.guillermo.uberroutes.login.data.datastore.UserPreferenceService
import baeza.guillermo.uberroutes.profile.data.network.dto.CommentDTO
import baeza.guillermo.uberroutes.profile.data.network.dto.PostCommentDTO
import baeza.guillermo.uberroutes.profile.data.network.dto.PostsDTO
import baeza.guillermo.uberroutes.profile.data.network.dto.RequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val profileClient: ProfileClient,
    private val preferences: UserPreferenceService
) {
    suspend fun getPosts(userId: String): List<PostsDTO?> {
        return withContext(Dispatchers.IO) {
            val response = profileClient.getPosts(RequestDTO(userId), token = "bearer: ${preferences.getToken("token")}")
            val list: MutableList<PostsDTO?> = mutableListOf()
            Log.i("DAM", "data: ${response.body()}")
            response.body()?.data?.forEach {
                list.add(
                    PostsDTO(
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
            (if (response.body()?.status!! == "200") {
                list
            } else {
                null
            })!!
        }
    }

    suspend fun getFollowers(id: String): List<String> {
        return withContext(Dispatchers.IO) {
            val response = profileClient.getUser(id, token = "bearer: ${preferences.getToken("token")}")
            Log.i("DAM", "data: ${response.body()}")
            val list: List<String> = response.body()?.data?.followers!!
            (if (response.body()?.status!! == "200") {
                list
            } else {
                null
            })!!
        }
    }

    suspend fun getFollowing(id: String): List<String> {
        return withContext(Dispatchers.IO) {
            val response = profileClient.getUser(id, token = "bearer: ${preferences.getToken("token")}")
            Log.i("DAM", "data: ${response.body()}")
            val list: List<String> = response.body()?.data?.following!!
            (if (response.body()?.status!! == "200") {
                list
            } else {
                null
            })!!
        }
    }

    suspend fun getComments(id: String): List<CommentDTO?> {
        return withContext(Dispatchers.IO) {
            val response = profileClient.getComments(id, token = "bearer: ${preferences.getToken("token")}")
            val list: MutableList<CommentDTO?> = mutableListOf()
            Log.i("DAM", "data: ${response.body()}")
            response.body()?.data?.forEach {
                list.add(
                    CommentDTO(
                        date = it.date,
                        time = it.time,
                        description = it.description,
                        user = it.user,
                        post = it.post
                    )
                )
            }
            (if (response.body()?.status!! == "200") {
                list
            } else {
                null
            })!!
        }
    }

    suspend fun postComment(description: String, userId: String, postId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val data = PostCommentDTO(description, userId, postId)
            val response = profileClient.postComment(data, token = "bearer: ${preferences.getToken("token")}")
            Log.i("DAM", "Comment posted = ${response.body()}")
            (response.body()!!.status == "200")
        }
    }
}