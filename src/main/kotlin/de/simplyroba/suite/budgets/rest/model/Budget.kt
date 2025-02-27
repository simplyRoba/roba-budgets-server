package de.simplyroba.suite.budgets.rest.model

data class Budget(
	val id: Long,
	val name: String,
	val monthlySavingAmountInCents: Int,
	val totalSavedAmountInCents: Int,
	val categoryId: Long,
)

data class BudgetCreate(
	val name: String,
	val monthlySavingAmountInCents: Int,
	val totalSavedAmountInCents: Int,
	val categoryId: Long,
)

typealias BudgetUpdate = BudgetCreate
