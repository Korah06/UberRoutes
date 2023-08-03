package baeza.guillermo.uberroutes.register.data.network

import baeza.guillermo.uberroutes.register.data.dto.RegisterDTO
import baeza.guillermo.uberroutes.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterClient {
    @POST("/users/register")
    suspend fun register(
        @Body user: RegisterDTO
    ): Response<RegisterResponse>
}