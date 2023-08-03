package baeza.guillermo.uberroutes.register.data.network

import android.util.Log
import baeza.guillermo.uberroutes.register.data.dto.RegisterDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val registerClient: RegisterClient
) {
    suspend fun register(
        user: RegisterDTO
    ): Boolean {
        return withContext(Dispatchers.IO) {
            val response = registerClient.register(
                user = user
            )
            Log.i("DAM", "data: ${response.body()}")
            response.body()?.status == "200"
        }
    }
}