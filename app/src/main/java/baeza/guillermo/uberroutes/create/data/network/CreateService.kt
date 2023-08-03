package baeza.guillermo.uberroutes.create.data.network

import android.util.Log
import baeza.guillermo.uberroutes.create.data.network.dto.PostDTO
import baeza.guillermo.uberroutes.login.data.datastore.UserPreferenceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateService @Inject constructor(
    private val createClient: CreateClient,
    private val preferences: UserPreferenceService
) {
    suspend fun postRoute(post: PostDTO): Boolean {
        return withContext(Dispatchers.IO) {
            val response = createClient.postRoute(data = post, token = "bearer: ${preferences.getToken("token")}")
            Log.i("DAM", "Route posted = ${response.body()}")
            (response.body()!!.status == "200")
        }
    }
}