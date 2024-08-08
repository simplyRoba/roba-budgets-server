package de.simplyroba.suite.budgets.persistence.model

import java.time.OffsetDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("expense")
data class ExpenseEntity(
  @Id val id: Long = 0,
  var title: String?,
  var amountInCents: Int,
  var dueDate: OffsetDateTime,
  var type: ExpenseType,
  var categoryId: Long,
  var budgetId: Long?,
)

enum class ExpenseType {
  FIX,
  FIX_FROM_BUDGET, // TODO or just FIX
  FLEX,
  BUDGET,
}