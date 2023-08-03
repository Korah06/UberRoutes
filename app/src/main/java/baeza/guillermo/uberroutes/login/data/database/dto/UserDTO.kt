package baeza.guillermo.uberroutes.login.data.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDTO(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id") val id: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("surname") val surname: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("following") val following: String,
    @ColumnInfo("followers") val followers: String,
    @ColumnInfo("picture") val picture: String,
    @ColumnInfo("register") val register: String,
    @ColumnInfo("web") val web: String,
    @ColumnInfo("admin") val admin: Boolean,
    @ColumnInfo("token") val token: String
)

fun baeza.guillermo.uberroutes.login.data.network.dto.UserDTO.toDataBase(token: String) =
    UserDTO(
        id = _id,
        name = name,
        surname = surname,
        email = email,
        following = following.size.toString(),
        followers = followers.size.toString(),
        picture = picture,
        register = register,
        web = web,
        admin = admin,
        token = token
    )