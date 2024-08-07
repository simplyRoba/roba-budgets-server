package de.simplyroba.suite.budgets.persistence

import java.time.OffsetDateTime
import org.springframework.data.annotation.Id

data class Expense(
  @Id val id: Long = 0,
  var amountInCents: Int,
  var dueDate: OffsetDateTime,
  var type: ExpenseType,
)

enum class ExpenseType {
  FIX,
  FLEX
}
