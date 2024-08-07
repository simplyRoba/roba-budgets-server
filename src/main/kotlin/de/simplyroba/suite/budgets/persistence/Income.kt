package de.simplyroba.suite.budgets.persistence

import java.time.OffsetDateTime
import org.springframework.data.annotation.Id

data class Income(
  @Id val id: Long = 0,
  var title: String,
  var amountInCents: Int,
  var dueDate: OffsetDateTime,
)
