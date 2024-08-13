package de.simplyroba.suite.budgets.rest.model

data class IncomeTemplate(
  val id: Long,
  val title: String,
  val amountInCents: Int,
  val repeatInterval: RepeatInterval,
)

enum class RepeatInterval {
  MONTHLY,
  QUARTERLY,
  SEMI_ANNUALLY,
  ANNUALLY,
}

data class IncomeTemplateCreate(
  val title: String,
  val amountInCents: Int,
  val repeatInterval: RepeatInterval,
)

typealias IncomeTemplateUpdate = IncomeTemplateCreate
