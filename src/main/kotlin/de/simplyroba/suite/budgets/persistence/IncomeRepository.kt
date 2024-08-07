package de.simplyroba.suite.budgets.persistence

import java.time.OffsetDateTime
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface IncomeRepository : ReactiveCrudRepository<Income, Long> {

  fun findAllByDueDateBetween(startDate: OffsetDateTime, endDate: OffsetDateTime): Flux<Income>
}
