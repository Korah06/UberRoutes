package baeza.guillermo.uberroutes.home.domain.usecase

import baeza.guillermo.uberroutes.home.data.HomeRepository
import baeza.guillermo.uberroutes.home.domain.entity.CommentModel
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: HomeRepository){
    suspend operator fun invoke(id: String) : List<CommentModel> {
        return repository.getComments(id)
    }
}