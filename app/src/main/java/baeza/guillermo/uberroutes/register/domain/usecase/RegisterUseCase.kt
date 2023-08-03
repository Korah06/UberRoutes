package baeza.guillermo.uberroutes.register.domain.usecase

import baeza.guillermo.uberroutes.register.data.RegisterRepository
import baeza.guillermo.uberroutes.register.data.dto.RegisterDTO
import baeza.guillermo.uberroutes.register.domain.entity.UserModel
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {
    suspend operator fun invoke(user: UserModel): Boolean {
        return repository.register(
            user = RegisterDTO(
                id = user.id,
                name = user.name,
                surname = user.surname,
                password = user.password,
                email = user.email
            )
        )
    }
}