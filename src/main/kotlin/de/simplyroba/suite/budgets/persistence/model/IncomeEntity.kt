package de.simplyroba.suite.budgets.persistence.model

import java.time.OffsetDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("income")
data class IncomeEntity(
  @Id val id: Long = 0,
  var title: String,
  var amountInCents: Int,
  var dueDate: OffsetDateTime,
)
