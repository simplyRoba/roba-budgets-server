package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.ExpenseRepository
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import de.simplyroba.suite.budgets.rest.model.ExpenseUpdate
import de.simplyroba.suite.budgets.service.converter.Converter
import de.simplyroba.suite.budgets.service.converter.ExpenseTypeToExpenseEntityTypeConverter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ExpenseService(
  private val expenseRepository: ExpenseRepository,
  private val expenseEntityToExpenseConverter: Converter<ExpenseEntity, Expense>,
  private val expenseCreateToEntityConverter: Converter<ExpenseCreate, ExpenseEntity>,
  private val expenseTypeToExpenseEntityTypeConverter: Converter<ExpenseType, ExpenseEntityType>,
) {

  fun findAll(): Flux<Expense> {
    return expenseRepository.findAll().map(expenseEntityToExpenseConverter::convert)
  }

  fun findById(id: Long): Mono<Expense> {
    return expenseRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Expense with id $id not found")))
      .map(expenseEntityToExpenseConverter::convert)
  }

  @Transactional
  fun createExpense(expense: ExpenseCreate): Mono<Expense> {
    return expenseRepository
      .save(expenseCreateToEntityConverter.convert(expense))
      .map(expenseEntityToExpenseConverter::convert)
  }

  @Transactional
  fun updateExpense(id: Long, expenseUpdate: ExpenseUpdate): Mono<Expense> {
    return expenseRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Expense with id $id not found")))
      .map { existingExpense ->
        existingExpense.apply {
            title = expenseUpdate.title
            amountInCents = expenseUpdate.amountInCents
            dueDate = expenseUpdate.dueDate
            type = expenseTypeToExpenseEntityTypeConverter.convert(expenseUpdate.type)
            categoryId = expenseUpdate.categoryId
            budgetId = expenseUpdate.budgetId
        }
      }
      .flatMap(expenseRepository::save)
      .map(expenseEntityToExpenseConverter::convert)
  }

  @Transactional
  fun deleteExpense(id: Long): Mono<Void> {
    return expenseRepository.deleteById(id)
  }
}
