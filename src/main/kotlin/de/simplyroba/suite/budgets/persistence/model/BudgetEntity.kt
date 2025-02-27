package de.simplyroba.suite.budgets.persistence.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("budget")
data class BudgetEntity(
	@Id val id: Long = 0,
	var name: String,
	var monthlySavingAmountInCents: Int,
	var totalSavedAmountInCents: Int,
	var categoryId: Long,
)
