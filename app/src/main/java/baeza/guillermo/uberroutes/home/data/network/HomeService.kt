package baeza.guillermo.uberroutes.home.data.network

import android.util.Log
import baeza.guillermo.uberroutes.home.data.network.dto.CommentDTO
import baeza.guillermo.uberroutes.home.data.network.dto.HomeDTO
import baeza.guillermo.uberroutes.home.data.network.dto.PostCommentDTO
import baeza.guillermo.uberroutes.login.data.datastore.UserPreferenceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeService @Inject constructor(
    private val homeClient: HomeClient,
    private val preferences: UserPreferenceService
) {
    suspend fun getPosts(): List<HomeDTO?> {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getPosts(token = "bearer: ${preferences.getToken("token")}")
            val list: MutableList<HomeDTO?> = mutableListOf()
            Log.i("DAM", "data: ${response.body()}")
            response.body()?.data?.forEach {
                list.add(
                    HomeDTO(
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

    suspend fun getComments(id: String): List<CommentDTO?> {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getComments(id, token = "bearer: ${preferences.getToken("token")}")
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
            val response = homeClient.postComment(data, token = "bearer: ${preferences.getToken("token")}")
            Log.i("DAM", "Comment posted = ${response.body()}")
            (response.body()!!.status == "200")
        }
    }
}