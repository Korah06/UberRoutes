package baeza.guillermo.uberroutes.home.domain.usecase

import baeza.guillermo.uberroutes.home.data.HomeRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(private val repository: HomeRepository){
    suspend operator fun invoke(description: String, userId: String, postId: String) : Boolean {
        return repository.postComment(description, userId, postId)
    }
}