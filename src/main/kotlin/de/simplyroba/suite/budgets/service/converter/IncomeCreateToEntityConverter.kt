package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import org.springframework.stereotype.Component

@Component
class IncomeCreateToEntityConverter : Converter<IncomeCreate, IncomeEntity> {
  override fun convert(source: IncomeCreate): IncomeEntity {
    return IncomeEntity(
      title = source.title,
      amountInCents = source.amountInCents,
      dueDate = source.dueDate
    )
  }
}
