package baeza.guillermo.uberroutes.profile.domain.usecase

import baeza.guillermo.uberroutes.profile.data.ProfileRepository
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(id: String) : List<String> {
        return repository.getFollowing(id)
    }
}