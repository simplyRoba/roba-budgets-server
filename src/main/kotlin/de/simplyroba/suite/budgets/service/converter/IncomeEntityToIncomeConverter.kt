package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import de.simplyroba.suite.budgets.rest.model.Income
import org.springframework.stereotype.Component

@Component
class IncomeEntityToIncomeConverter : Converter<IncomeEntity, Income> {
  override fun convert(source: IncomeEntity): Income {
    return Income(
      id = source.id,
      title = source.title,
      amountInCents = source.amountInCents,
      dueDate = source.dueDate
    )
  }
}
