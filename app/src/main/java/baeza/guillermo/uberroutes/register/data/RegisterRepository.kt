package baeza.guillermo.uberroutes.register.data

import baeza.guillermo.uberroutes.register.data.dto.RegisterDTO
import baeza.guillermo.uberroutes.register.data.network.RegisterService
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api: RegisterService) {
    suspend fun register(
        user: RegisterDTO
    ): Boolean {
        return api.register(
            user = user
        )
    }
}