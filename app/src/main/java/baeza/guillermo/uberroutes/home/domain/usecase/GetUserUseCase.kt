package baeza.guillermo.uberroutes.home.domain.usecase

import baeza.guillermo.uberroutes.home.data.HomeRepository
import baeza.guillermo.uberroutes.home.domain.entity.UserModel
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke() : UserModel {
        return repository.getUser()
    }
}