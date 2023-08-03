package baeza.guillermo.uberroutes.profile.domain.usecase

import baeza.guillermo.uberroutes.profile.data.ProfileRepository
import baeza.guillermo.uberroutes.profile.domain.entity.CommentModel
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(id: String) : List<CommentModel> {
        return repository.getComments(id)
    }
}