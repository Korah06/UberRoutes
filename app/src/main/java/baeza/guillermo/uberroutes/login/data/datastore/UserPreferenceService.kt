package baeza.guillermo.uberroutes.login.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferenceService @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IUserPreferences {
    override suspend fun addToken(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    override suspend fun getToken(key: String): String {
        return try {
            val preferenceKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
            preferences[preferenceKey] ?: ""
        } catch (e: Exception) {
            Log.e("DAM", "error: ${e.message}")
            ""
        }
    }
}