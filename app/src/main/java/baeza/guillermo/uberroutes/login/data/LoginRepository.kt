package baeza.guillermo.uberroutes.login.data

import android.util.Log
import baeza.guillermo.uberroutes.login.data.database.dao.UserDAO
import baeza.guillermo.uberroutes.login.data.database.dto.toDataBase
import baeza.guillermo.uberroutes.login.data.datastore.UserPreferenceService
import baeza.guillermo.uberroutes.login.data.network.LoginService
import baeza.guillermo.uberroutes.login.data.network.dto.UserDTO
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginService,
    private val userPreference: UserPreferenceService,
    private val userDAO: UserDAO
){
    suspend fun doLogin(user1: String, password: String): Boolean {
        val user: UserDTO? = api.doLogin(user1, password)
        if (user != null) {
            userPreference.addToken("token", user.token)
            userDAO.insertAll(
                listOf(user.toDataBase(userPreference.getToken("token")))
            )
            return true
        }
        return false
    }

    suspend fun getConnectionToken(): Boolean {
        return if (!userPreference.getToken("token").isNullOrEmpty()) {
            Log.i("DAM", "El token es: ${userPreference.getToken("token")}")
            true
        } else
            false
    }
}