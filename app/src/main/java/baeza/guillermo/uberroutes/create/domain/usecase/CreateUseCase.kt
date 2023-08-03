package baeza.guillermo.uberroutes.create.domain.usecase

import baeza.guillermo.uberroutes.create.data.CreateRepository
import baeza.guillermo.uberroutes.create.domain.entity.RouteModel
import javax.inject.Inject

class CreateUseCase @Inject constructor(private val repository: CreateRepository) {
    suspend operator fun invoke(post: RouteModel): Boolean {
        return repository.postRoute(post)
    }
}