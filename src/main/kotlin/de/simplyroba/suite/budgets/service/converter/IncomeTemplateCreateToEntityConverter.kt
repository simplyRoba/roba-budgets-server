package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalEnum
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateCreate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class IncomeTemplateCreateToEntityConverter(
  private val repeatIntervalToRepeatIntervalEnumConverter:
    Converter<RepeatInterval, RepeatIntervalEnum>
) : Converter<IncomeTemplateCreate, IncomeTemplateEntity> {
  override fun convert(source: IncomeTemplateCreate): IncomeTemplateEntity {
    return IncomeTemplateEntity(
      title = source.title,
      amountInCents = source.amountInCents,
      repeatInterval = repeatIntervalToRepeatIntervalEnumConverter.convert(source.repeatInterval),
    )
  }
}
