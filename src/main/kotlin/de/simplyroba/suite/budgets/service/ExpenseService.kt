package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.ExpenseRepository
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import de.simplyroba.suite.budgets.service.converter.Converter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ExpenseService(
  private val expenseRepository: ExpenseRepository,
  private val expenseEntityToExpenseConverter: Converter<ExpenseEntity, Expense>,
  private val expenseTypeToExpenseEntityTypeConverter: Converter<ExpenseType, ExpenseEntityType>
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
      .save(
        ExpenseEntity(
          title = expense.title,
          amountInCents = expense.amountInCents,
          dueDate = expense.dueDate,
          type = expenseTypeToExpenseEntityTypeConverter.convert(expense.type),
          categoryId = expense.categoryId,
          budgetId = expense.budgetId
        )
      )
      .map(expenseEntityToExpenseConverter::convert)
  }

  //    @Transactional
  //    fun updateExpense(id: Long, expenseUpdate: ExpenseCreate): Mono<Expense> {
  //        return expenseRepository
  //            .findById(id)
  //            .switchIfEmpty(Mono.error(NotFoundError("Expense with id $id not found")))
  //            .map { existingExpense ->
  //                existingExpense.apply {
  //                    name = expenseUpdate.name
  //                    amountInCents = expenseUpdate.amountInCents
  //                    date = expenseUpdate.date
  //                    budgetId = expenseUpdate.budgetId
  //                }
  //            }
  //            .flatMap(expenseRepository::save)
  //            .map(expenseEntityToExpenseConverter::convert)
  //    }

  @Transactional
  fun deleteExpense(id: Long): Mono<Void> {
    return expenseRepository.deleteById(id)
  }
}
