package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.EntityRepeatInterval
import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import de.simplyroba.suite.budgets.rest.model.IncomeTemplate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class IncomeTemplateEntityToIncomeTemplateConverter(
  private val entityRepeatIntervalToRepeatIntervalConverter:
    Converter<EntityRepeatInterval, RepeatInterval>
) : Converter<IncomeTemplateEntity, IncomeTemplate> {

  override fun convert(source: IncomeTemplateEntity): IncomeTemplate {
    return IncomeTemplate(
      id = source.id,
      title = source.title,
      amountInCents = source.amountInCents,
      repeatInterval = entityRepeatIntervalToRepeatIntervalConverter.convert(source.repeatInterval),
    )
  }
}
