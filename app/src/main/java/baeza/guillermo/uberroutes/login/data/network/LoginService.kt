package baeza.guillermo.uberroutes.login.data.network

import android.util.Log
import baeza.guillermo.uberroutes.login.data.datastore.UserPreferenceService
import baeza.guillermo.uberroutes.login.data.network.dto.LoginDTO
import baeza.guillermo.uberroutes.login.data.network.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(
    private val loginClient: LoginClient,
    private val userPreference: UserPreferenceService
) {

    suspend fun doLogin(user: String, password: String) : UserDTO? {
        return withContext(Dispatchers.IO) {
            val data = LoginDTO(user, password)
            val response = loginClient.doLogin(data)
            Log.i("ERERERERERER", "data: ${response.errorBody()}")
            Log.i("DAM", "data: ${response.body()}")
            response.body()?.token?.let { userPreference.addToken("token", it) }
            if (response.errorBody() == null && response.body()?.status!! == "200") {
                UserDTO(
                    _id = response.body()?.data?._id!!,
                    name = response.body()?.data?.name!!,
                    surname = response.body()?.data?.surname!!,
                    email = response.body()?.data?.email!!,
                    following = response.body()?.data?.following!!,
                    followers = response.body()?.data?.followers!!,
                    picture = response.body()?.data?.picture!!,
                    register = response.body()?.data?.register!!,
                    web = response.body()?.data?.web!!,
                    admin = response.body()?.data?.admin!!,
                    token = response.body()?.token!!
                )
            } else {
                null
            }
        }
    }
}