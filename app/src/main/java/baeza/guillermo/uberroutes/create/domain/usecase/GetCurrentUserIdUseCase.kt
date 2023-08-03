package baeza.guillermo.uberroutes.create.domain.usecase

import baeza.guillermo.uberroutes.create.data.CreateRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(private val repository: CreateRepository) {
    suspend operator fun invoke(): String {
        return repository.getCurrentUserId()
    }
}