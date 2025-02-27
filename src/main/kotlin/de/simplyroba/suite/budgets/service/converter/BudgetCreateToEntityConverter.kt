package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import org.springframework.stereotype.Component

@Component
class BudgetCreateToEntityConverter : Converter<BudgetCreate, BudgetEntity> {
  override fun convert(source: BudgetCreate): BudgetEntity {
    return BudgetEntity(
      name = source.name,
      monthlySavingAmountInCents = source.monthlySavingAmountInCents,
      totalSavedAmountInCents = source.totalSavedAmountInCents,
      categoryId = source.categoryId,
    )
  }
}
