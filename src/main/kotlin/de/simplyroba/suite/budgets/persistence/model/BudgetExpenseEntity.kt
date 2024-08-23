package de.simplyroba.suite.budgets.persistence.model

import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("budget_expense")
data class BudgetExpenseEntity(
  @Id val id: Long = 0,
  var title: String?,
  var amountInCents: Int,
  var dueDate: LocalDate,
  var categoryId: Long,
  var budgetId: Long?,
)
