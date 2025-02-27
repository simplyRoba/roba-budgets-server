package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.FixExpenseTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class FixExpenseTemplateEntityToDtoConverter(
  private val repeatIntervalPersistenceEnumToDtoConverter:
    Converter<RepeatIntervalPersistenceEnum, RepeatInterval>
) : Converter<FixExpenseTemplateEntity, FixExpenseTemplate> {
  override fun convert(source: FixExpenseTemplateEntity): FixExpenseTemplate {
    return FixExpenseTemplate(
      id = source.id,
      title = source.title,
      amountInCents = source.amountInCents,
      repeatInterval = repeatIntervalPersistenceEnumToDtoConverter.convert(source.repeatInterval),
      categoryId = source.categoryId,
    )
  }
}
