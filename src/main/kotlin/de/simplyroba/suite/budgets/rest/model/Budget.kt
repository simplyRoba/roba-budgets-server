package de.simplyroba.suite.budgets.rest.model

data class Budget(
  val id: Long,
  val title: String,
  val savingAmountInCents: Int,
  val categoryId: Long,
)

data class BudgetCreate(
  val title: String,
  val savingAmountInCents: Int,
  val categoryId: Long,
)

typealias BudgetUpdate = BudgetCreate
