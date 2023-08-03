package baeza.guillermo.uberroutes.home.data.network

import baeza.guillermo.uberroutes.home.data.network.dto.PostCommentDTO
import baeza.guillermo.uberroutes.home.data.network.response.CommentResponse
import baeza.guillermo.uberroutes.home.data.network.response.HomeResponse
import baeza.guillermo.uberroutes.home.data.network.response.PostCommentResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeClient {
    @GET("/posts")
    suspend fun getPosts(
        @Header("authorization") token: String
    ): Response<HomeResponse>

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