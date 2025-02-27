package de.simplyroba.suite.budgets.rest.model

data class IncomeTemplate(
	val id: Long,
	val title: String,
	val amountInCents: Int,
	val repeatInterval: RepeatInterval,
)

data class IncomeTemplateCreate(
	val title: String,
	val amountInCents: Int,
	val repeatInterval: RepeatInterval,
)

typealias IncomeTemplateUpdate = IncomeTemplateCreate
