package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import java.time.OffsetDateTime
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ExpenseRepository : ReactiveCrudRepository<ExpenseEntity, Long> {

  fun findAllByDueDateBetween(
    startDate: OffsetDateTime,
    endDate: OffsetDateTime
  ): Flux<ExpenseEntity>

  fun findAllByType(type: ExpenseEntityType): Flux<ExpenseEntity>

  fun findAllByTypeAndDueDateBetween(
    type: ExpenseEntityType,
    startDate: OffsetDateTime,
    endDate: OffsetDateTime
  ): Flux<ExpenseEntity>

  fun findAllByCategoryId(categoryId: Long): Flux<ExpenseEntity>

  fun findAllByCategoryIdAndDueDateBetween(
    categoryId: Long,
    startDate: OffsetDateTime,
    endDate: OffsetDateTime
  ): Flux<ExpenseEntity>

  fun findAllByBudgetId(budgetId: Long): Flux<ExpenseEntity>

  fun findAllByBudgetIdAndDueDateBetween(
    budgetId: Long,
    startDate: OffsetDateTime,
    endDate: OffsetDateTime
  ): Flux<ExpenseEntity>
}
