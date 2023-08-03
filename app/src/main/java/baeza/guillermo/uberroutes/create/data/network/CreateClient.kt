package baeza.guillermo.uberroutes.create.data.network

import baeza.guillermo.uberroutes.create.data.network.dto.PostDTO
import baeza.guillermo.uberroutes.create.data.network.response.CreateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CreateClient {
    @POST("/posts/create")
    suspend fun postRoute(
        @Body data: PostDTO,
        @Header("authorization") token: String
    ): Response<CreateResponse>
}