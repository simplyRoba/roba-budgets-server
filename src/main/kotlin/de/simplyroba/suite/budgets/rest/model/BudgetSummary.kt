package de.simplyroba.suite.budgets.rest.model

data class BudgetSummary(
	val id: Long,
	val name: String,
	val totalSavedAmountInCents: Int,
	val totalExpensesInCents: Int,
)
