package baeza.guillermo.uberroutes.profile.domain.usecase

import baeza.guillermo.uberroutes.profile.data.ProfileRepository
import baeza.guillermo.uberroutes.profile.domain.entity.ProfilePostModel
import javax.inject.Inject

class GetProfileRoutesUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String) : List<ProfilePostModel> {
        return repository.getRoutes(userId)
    }
}