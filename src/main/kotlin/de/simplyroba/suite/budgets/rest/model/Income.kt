package de.simplyroba.suite.budgets.rest.model

import java.time.OffsetDateTime

data class Income(
  val id: Long,
  val title: String,
  val amountInCents: Int,
  val dueDate: OffsetDateTime,
)

data class IncomeCreate(
  val title: String,
  val amountInCents: Int,
  val dueDate: OffsetDateTime,
)

typealias IncomeUpdate = IncomeCreate
