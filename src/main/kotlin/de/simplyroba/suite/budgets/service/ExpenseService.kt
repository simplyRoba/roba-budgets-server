package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.ExpenseRepository
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import de.simplyroba.suite.budgets.rest.model.ExpenseUpdate
import de.simplyroba.suite.budgets.service.converter.Converter
import java.time.LocalDate
import java.time.YearMonth
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ExpenseService(
  private val expenseRepository: ExpenseRepository,
  private val expenseEntityToDtoConverter: Converter<ExpenseEntity, Expense>,
  private val expenseCreateToEntityConverter: Converter<ExpenseCreate, ExpenseEntity>,
  private val expenseTypeToPersistenceEnumConverter:
    Converter<ExpenseType, ExpenseTypePersistenceEnum>,
) {

  fun findAll(): Flux<Expense> {
    return expenseRepository.findAll().map(expenseEntityToDtoConverter::convert)
  }

  fun findAllByTypeYearAndMonth(type: ExpenseType, year: Int, month: Int): Flux<Expense> {
    val yearMonth = YearMonth.of(year, month)
    return expenseRepository
      .findAllByTypeAndDueDateBetween(
        expenseTypeToPersistenceEnumConverter.convert(type),
        yearMonth.atDay(1),
        yearMonth.atEndOfMonth()
      )
      .map(expenseEntityToDtoConverter::convert)
  }

  fun findAllCategory(categoryId: Long): Flux<Expense> {
    return expenseRepository
      .findAllByCategoryId(categoryId)
      .map(expenseEntityToDtoConverter::convert)
  }

  fun findAllCategoryBetweenDates(
    categoryId: Long,
    startDate: LocalDate,
    endDate: LocalDate
  ): Flux<Expense> {
    return expenseRepository
      .findAllByCategoryIdAndDueDateBetween(categoryId, startDate, endDate)
      .map(expenseEntityToDtoConverter::convert)
  }

  fun findAllByBudget(budgetId: Long): Flux<Expense> {
    return expenseRepository.findAllByBudgetId(budgetId).map(expenseEntityToDtoConverter::convert)
  }

  fun findAllByBudgetBetweenDates(
    budgetId: Long,
    startDate: LocalDate,
    endDate: LocalDate
  ): Flux<Expense> {
    return expenseRepository
      .findAllByBudgetIdAndDueDateBetween(budgetId, startDate, endDate)
      .map(expenseEntityToDtoConverter::convert)
  }

  fun findByIdAndType(id: Long, type: ExpenseType): Mono<Expense> {
    return expenseRepository
      .findByIdAndType(id, expenseTypeToPersistenceEnumConverter.convert(type))
      .switchIfEmpty(Mono.error(NotFoundError("Expense with id $id and type $type not found")))
      .map(expenseEntityToDtoConverter::convert)
  }

  @Transactional
  fun createExpense(expense: ExpenseCreate): Mono<Expense> {
    return expenseRepository
      .save(expenseCreateToEntityConverter.convert(expense))
      .map(expenseEntityToDtoConverter::convert)
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
          type = expenseTypeToPersistenceEnumConverter.convert(expenseUpdate.type)
          categoryId = expenseUpdate.categoryId
          budgetId = expenseUpdate.budgetId
        }
      }
      .flatMap(expenseRepository::save)
      .map(expenseEntityToDtoConverter::convert)
  }

  @Transactional
  fun deleteExpense(id: Long): Mono<Void> {
    return expenseRepository.deleteById(id)
  }
}
