package baeza.guillermo.uberroutes.profile.data.network

import baeza.guillermo.uberroutes.profile.data.network.dto.PostCommentDTO
import baeza.guillermo.uberroutes.profile.data.network.dto.RequestDTO
import baeza.guillermo.uberroutes.profile.data.network.response.CommentResponse
import baeza.guillermo.uberroutes.profile.data.network.response.FollowResponse
import baeza.guillermo.uberroutes.profile.data.network.response.PostCommentResponse
import baeza.guillermo.uberroutes.profile.data.network.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileClient {
    @POST("/posts/byuser")
    suspend fun getPosts(
        @Body data: RequestDTO,
        @Header("authorization") token: String
    ): Response<ProfileResponse>

    @GET("/users/{id}")
    suspend fun getUser(
        @Path("id") id: String,
        @Header("authorization") token: String
    ): Response<FollowResponse>

    @GET("/comments/{id}")
    suspend fun getComments(
        @Path("id") id: String,
        @Header("authorization") token: String
    ): Response<CommentResponse>

    @POST("/comments")
    suspend fun postComment(
        @Body data: PostCommentDTO,
        @Header("authorization") token: String
    ): Response<PostCommentResponse>
}
