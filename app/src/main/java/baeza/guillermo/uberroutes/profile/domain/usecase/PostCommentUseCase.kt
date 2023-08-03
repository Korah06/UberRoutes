package baeza.guillermo.uberroutes.profile.domain.usecase

import baeza.guillermo.uberroutes.profile.data.ProfileRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(description: String, userId: String, postId: String) : Boolean {
        return repository.postComment(description, userId, postId)
    }
}
