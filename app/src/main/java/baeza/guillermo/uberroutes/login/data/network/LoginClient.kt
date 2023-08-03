package baeza.guillermo.uberroutes.login.data.network

import baeza.guillermo.uberroutes.login.data.network.dto.LoginDTO
import baeza.guillermo.uberroutes.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginClient {
    @POST("/users/login")
    suspend fun doLogin(
        @Body data: LoginDTO
    ): Response<LoginResponse>
}