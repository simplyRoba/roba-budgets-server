package de.simplyroba.suite.budgets.rest.model

data class FixExpenseTemplate(
  val id: Long = 0,
  var title: String,
  var amountInCents: Int,
  var repeatInterval: RepeatInterval,
  var categoryId: Long,
)

data class FixExpenseTemplateCreate(
  var title: String,
  var amountInCents: Int,
  var repeatInterval: RepeatInterval,
  var categoryId: Long,
)

typealias FixExpenseTemplateUpdate = FixExpenseTemplateCreate
