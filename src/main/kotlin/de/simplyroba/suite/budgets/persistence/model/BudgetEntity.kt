package de.simplyroba.suite.budgets.persistence.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("budget_template")
data class BudgetEntity(
  @Id val id: Long = 0,
  var title: String,
  var savingAmountInCents: Int,
  var categoryId: Long,
)
