package baeza.guillermo.uberroutes.dependencyinjection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import baeza.guillermo.uberroutes.login.data.database.UserDataBase
import baeza.guillermo.uberroutes.login.data.database.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"
private const val UBER_ROUTES_DATABASE_NAME = "uberRoutesDB"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideContex(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideRoom(context: Context) =
        Room.databaseBuilder(context, UserDataBase::class.java, UBER_ROUTES_DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideUserDAO(db: UserDataBase) : UserDAO = db.getUserDAO()
}