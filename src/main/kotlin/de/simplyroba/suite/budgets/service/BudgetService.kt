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
  private val budgetEntityToBudgetConverter: Converter<BudgetEntity, Budget>,
) {

  fun findAll(): Flux<Budget> {
    return budgetRepository.findAll().map(budgetEntityToBudgetConverter::convert)
  }

  fun findById(id: Long): Mono<Budget> {
    return budgetRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Budget with id $id not found")))
      .map(budgetEntityToBudgetConverter::convert)
  }

  @Transactional
  fun createBudget(budget: BudgetCreate): Mono<Budget> {
    return budgetRepository
      .save(
        BudgetEntity(
          name = budget.name,
          savingAmountInCents = budget.savingAmountInCents,
        )
      )
      .map(budgetEntityToBudgetConverter::convert)
  }

  @Transactional
  fun updateBudget(id: Long, budgetUpdate: BudgetCreate): Mono<Budget> {
    return budgetRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Budget with id $id not found")))
      .map { existingBudget ->
        existingBudget.apply {
          name = budgetUpdate.name
          savingAmountInCents = budgetUpdate.savingAmountInCents
        }
      }
      .flatMap(budgetRepository::save)
      .map(budgetEntityToBudgetConverter::convert)
  }

  @Transactional
  fun deleteBudget(id: Long): Mono<Void> {
    return budgetRepository.deleteById(id)
  }
}
