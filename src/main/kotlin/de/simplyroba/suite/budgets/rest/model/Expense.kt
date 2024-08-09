package de.simplyroba.suite.budgets.rest.model

import java.time.OffsetDateTime

data class Expense(
  val id: Long,
  val title: String?,
  val amountInCents: Int,
  val dueDate: OffsetDateTime,
  val type: ExpenseType,
  val categoryId: Long,
  val budgetId: Long?,
)

enum class ExpenseType {
  FIX,
  FIX_FROM_BUDGET, // TODO or just FIX
  FLEX,
  BUDGET,
}

data class ExpenseCreate(
  val title: String?,
  val amountInCents: Int,
  val dueDate: OffsetDateTime,
  val type: ExpenseType,
  val categoryId: Long,
  val budgetId: Long?,
)

typealias ExpenseUpdate = ExpenseCreate
