package de.simplyroba.suite.budgets.persistence.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("income_template")
data class IncomeTemplateEntity(
  @Id val id: Long = 0,
  var title: String,
  var amountInCents: Int,
  var repeatInterval: EntityRepeatInterval,
)

enum class EntityRepeatInterval {
  MONTHLY,
  QUARTERLY,
  SEMI_ANNUALLY,
  ANNUALLY
}
