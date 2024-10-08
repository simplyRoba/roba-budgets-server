package de.simplyroba.suite.budgets.persistence.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("fix_expense_template")
data class FixExpenseTemplateEntity(
  @Id val id: Long = 0,
  var title: String,
  var amountInCents: Int,
  var repeatInterval: RepeatIntervalPersistenceEnum,
  var categoryId: Long,
)
