package de.simplyroba.suite.budgets.persistence.model

enum class RepeatIntervalEnum {
  ONCE, // TODO this makes no sense in the db as it would be a direkt entry to income
  MONTHLY,
  QUARTERLY,
  SEMI_ANNUALLY,
  ANNUALLY
}
