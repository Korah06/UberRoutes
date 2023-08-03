package baeza.guillermo.uberroutes.login.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import baeza.guillermo.uberroutes.login.data.database.dto.UserDTO

@Dao
interface UserDAO {
    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users : List<UserDTO>)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}