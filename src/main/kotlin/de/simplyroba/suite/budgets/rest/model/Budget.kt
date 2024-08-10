package de.simplyroba.suite.budgets.rest.model

data class Budget(
  val id: Long,
  val name: String,
  val savingAmountInCents: Int,
)

data class BudgetCreate(
  val name: String,
  val savingAmountInCents: Int,
)

typealias BudgetUpdate = BudgetCreate
