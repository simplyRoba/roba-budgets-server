package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import java.time.LocalDate
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface IncomeRepository : ReactiveCrudRepository<IncomeEntity, Long> {

	fun findAllByDueDateBetween(startDate: LocalDate, endDate: LocalDate): Flux<IncomeEntity>
}
