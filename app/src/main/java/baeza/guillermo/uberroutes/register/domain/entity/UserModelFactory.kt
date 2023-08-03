package baeza.guillermo.uberroutes.register.domain.entity

import javax.inject.Inject

class UserModelFactory @Inject constructor(){
    operator fun invoke(
        id: String,
        name: String,
        surname: String,
        password: String,
        email: String
    ): UserModel {
        return UserModel(
            id = id,
            name = name,
            surname = surname,
            password = password,
            email = email
        )
    }
}