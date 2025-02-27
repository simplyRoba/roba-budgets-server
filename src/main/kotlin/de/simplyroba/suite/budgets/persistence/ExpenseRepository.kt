package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import java.time.LocalDate
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ExpenseRepository : ReactiveCrudRepository<ExpenseEntity, Long> {

  fun findAllByDueDateBetween(startDate: LocalDate, endDate: LocalDate): Flux<ExpenseEntity>

  fun findAllByType(type: ExpenseTypePersistenceEnum): Flux<ExpenseEntity>

  fun findAllByTypeAndDueDateBetween(
    type: ExpenseTypePersistenceEnum,
    startDate: LocalDate,
    endDate: LocalDate,
  ): Flux<ExpenseEntity>

  fun findAllByCategoryId(categoryId: Long): Flux<ExpenseEntity>

  fun findAllByCategoryIdAndDueDateBetween(
    categoryId: Long,
    startDate: LocalDate,
    endDate: LocalDate,
  ): Flux<ExpenseEntity>

  fun findAllByBudgetId(budgetId: Long): Flux<ExpenseEntity>

  fun findAllByBudgetIdAndDueDateBetween(
    budgetId: Long,
    startDate: LocalDate,
    endDate: LocalDate,
  ): Flux<ExpenseEntity>
}
