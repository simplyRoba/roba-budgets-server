package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.BudgetRepository
import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.Budget
import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import de.simplyroba.suite.budgets.service.converter.Converter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BudgetService(
  private val budgetRepository: BudgetRepository,
  private val budgetEntityToDtoConverter: Converter<BudgetEntity, Budget>,
  private val budgetCreateToEntityConverter: Converter<BudgetCreate, BudgetEntity>
) {

  fun findAll(): Flux<Budget> {
    return budgetRepository.findAll().map(budgetEntityToDtoConverter::convert)
  }

  fun findById(id: Long): Mono<Budget> {
    return budgetRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Budget with id $id not found")))
      .map(budgetEntityToDtoConverter::convert)
  }

  @Transactional
  fun createBudget(budget: BudgetCreate): Mono<Budget> {
    return budgetRepository
      .save(budgetCreateToEntityConverter.convert(budget))
      .map(budgetEntityToDtoConverter::convert)
  }

  @Transactional
  fun updateBudget(id: Long, budgetUpdate: BudgetCreate): Mono<Budget> {
    return budgetRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Budget with id $id not found")))
      .map { existingBudget ->
        existingBudget.apply {
          name = budgetUpdate.name
          monthlySavingAmountInCents = budgetUpdate.monthlySavingAmountInCents
          totalSavedAmountInCents = budgetUpdate.totalSavedAmountInCents
          categoryId = budgetUpdate.categoryId
        }
      }
      .flatMap(budgetRepository::save)
      .map(budgetEntityToDtoConverter::convert)
  }

  @Transactional
  fun deleteBudget(id: Long): Mono<Void> {
    return budgetRepository.deleteById(id)
  }
}
