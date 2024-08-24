package de.simplyroba.suite.budgets.rest.model

import java.time.LocalDate

data class BudgetExpense(
  val id: Long,
  val title: String,
  val amountInCents: Int,
  val dueDate: LocalDate,
  val categoryId: Long,
  val budgetId: Long,
)

data class BudgetExpenseCreate(
  val title: String,
  val amountInCents: Int,
  val dueDate: LocalDate,
  val categoryId: Long,
  val budgetId: Long,
)

typealias BudgetExpenseUpdate = BudgetExpenseCreate
