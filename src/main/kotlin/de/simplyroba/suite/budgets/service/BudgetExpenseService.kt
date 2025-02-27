package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.BudgetExpenseRepository
import de.simplyroba.suite.budgets.persistence.model.BudgetExpenseEntity
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.BudgetExpense
import de.simplyroba.suite.budgets.rest.model.BudgetExpenseCreate
import de.simplyroba.suite.budgets.rest.model.BudgetExpenseUpdate
import de.simplyroba.suite.budgets.service.converter.Converter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BudgetExpenseService(
	private val budgetExpenseRepository: BudgetExpenseRepository,
	private val budgetExpenseEntityToDtoConverter: Converter<BudgetExpenseEntity, BudgetExpense>,
	private val budgetExpenseCreateToEntityConverter:
		Converter<BudgetExpenseCreate, BudgetExpenseEntity>,
) {

	fun findAll(): Flux<BudgetExpense> {
		return budgetExpenseRepository.findAll().map(budgetExpenseEntityToDtoConverter::convert)
	}

	fun findById(id: Long): Mono<BudgetExpense> {
		return budgetExpenseRepository
			.findById(id)
			.switchIfEmpty(Mono.error(NotFoundError("Budget Expense with id $id not found")))
			.map(budgetExpenseEntityToDtoConverter::convert)
	}

	@Transactional
	fun createBudgetExpense(budgetExpense: BudgetExpenseCreate): Mono<BudgetExpense> {
		return budgetExpenseRepository
			.save(budgetExpenseCreateToEntityConverter.convert(budgetExpense))
			.map(budgetExpenseEntityToDtoConverter::convert)
	}

	@Transactional
	fun updateBudgetExpense(id: Long, budgetExpense: BudgetExpenseUpdate): Mono<BudgetExpense> {
		return budgetExpenseRepository
			.findById(id)
			.switchIfEmpty(Mono.error(NotFoundError("Budget Expense with id $id not found")))
			.map { existingBudgetExpense ->
				existingBudgetExpense.apply {
					title = budgetExpense.title
					amountInCents = budgetExpense.amountInCents
					dueDate = budgetExpense.dueDate
					categoryId = budgetExpense.categoryId
					budgetId = budgetExpense.budgetId
				}
			}
			.flatMap(budgetExpenseRepository::save)
			.map(budgetExpenseEntityToDtoConverter::convert)
	}

	@Transactional
	fun deleteBudgetExpense(id: Long): Mono<Void> {
		return budgetExpenseRepository.deleteById(id)
	}
}
