package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import de.simplyroba.suite.budgets.rest.model.Budget
import org.springframework.stereotype.Component

@Component
class BudgetEntityToBudgetConverter : Converter<BudgetEntity, Budget> {
  override fun convert(source: BudgetEntity): Budget {
    return Budget(
      id = source.id,
      name = source.name,
      savingAmountInCents = source.savingAmountInCents
    )
  }
}
