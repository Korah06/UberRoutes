package baeza.guillermo.uberroutes.profile.domain.usecase

import baeza.guillermo.uberroutes.profile.data.ProfileRepository
import baeza.guillermo.uberroutes.profile.domain.entity.UserModel
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke() : UserModel {
        return repository.getUser()
    }
}