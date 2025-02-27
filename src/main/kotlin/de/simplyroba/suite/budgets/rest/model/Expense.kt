package de.simplyroba.suite.budgets.rest.model

import java.time.LocalDate

data class Expense(
  val id: Long,
  val title: String?,
  val amountInCents: Int,
  val dueDate: LocalDate,
  val type: ExpenseType,
  val categoryId: Long,
  val budgetId: Long?,
)

enum class ExpenseType {
  FIX,
  FLEX,
  BUDGET,
}

data class ExpenseCreate(
  val title: String?,
  val amountInCents: Int,
  val dueDate: LocalDate,
  val type: ExpenseType,
  val categoryId: Long,
  val budgetId: Long?,
)

typealias ExpenseUpdate = ExpenseCreate
