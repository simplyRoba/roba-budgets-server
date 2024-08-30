package de.simplyroba.suite.budgets.rest.model

data class MonthlySummary(
  val month: Int,
  val year: Int,
  val totalIncomeInCents: Int,
  val totalFixExpensesInCents: Int,
  val totalFlexExpensesInCents: Int,
)
