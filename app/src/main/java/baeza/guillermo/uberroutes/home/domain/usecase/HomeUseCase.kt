package baeza.guillermo.uberroutes.home.domain.usecase

import baeza.guillermo.uberroutes.home.data.HomeRepository
import baeza.guillermo.uberroutes.home.domain.entity.PostModel
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repository: HomeRepository){
    suspend operator fun invoke() : List<PostModel> {
        return repository.getRoutes()
    }
}