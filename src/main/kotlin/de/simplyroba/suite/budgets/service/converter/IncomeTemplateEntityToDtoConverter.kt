package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.IncomeTemplate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class IncomeTemplateEntityToDtoConverter(
  private val repeatIntervalPersistenceEnumToDtoConverter:
    Converter<RepeatIntervalPersistenceEnum, RepeatInterval>
) : Converter<IncomeTemplateEntity, IncomeTemplate> {

  override fun convert(source: IncomeTemplateEntity): IncomeTemplate {
    return IncomeTemplate(
      id = source.id,
      title = source.title,
      amountInCents = source.amountInCents,
      repeatInterval = repeatIntervalPersistenceEnumToDtoConverter.convert(source.repeatInterval),
    )
  }
}
