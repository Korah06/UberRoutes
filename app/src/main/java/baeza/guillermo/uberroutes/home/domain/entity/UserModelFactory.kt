package baeza.guillermo.uberroutes.home.domain.entity

import javax.inject.Inject

class UserModelFactory @Inject constructor() {
    operator fun invoke(
        _id: String,
        name: String,
        surname: String,
        email: String,
        following: String,
        followers: String,
        picture: String,
        register: String,
        web: String,
        admin: Boolean
    ): UserModel {
        return UserModel(
            _id = _id,
            name = name,
            surname = surname,
            email = email,
            following = following,
            followers = followers,
            picture = picture,
            register = register,
            web = web,
            admin = admin
        )
    }
}