package de.simplyroba.suite.budgets.persistence.model

import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("expense")
data class ExpenseEntity(
  @Id val id: Long = 0,
  var title: String?,
  var amountInCents: Int,
  var dueDate: LocalDate,
  var type: ExpenseTypePersistenceEnum,
  var categoryId: Long,
  var budgetId: Long?,
)
