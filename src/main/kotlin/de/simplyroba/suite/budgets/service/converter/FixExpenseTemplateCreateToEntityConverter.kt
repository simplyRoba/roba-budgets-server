package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.FixExpenseTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalEnum
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateCreate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class FixExpenseTemplateCreateToEntityConverter(
  private val repeatIntervalToRepeatIntervalEnumConverter:
    Converter<RepeatInterval, RepeatIntervalEnum>
) : Converter<FixExpenseTemplateCreate, FixExpenseTemplateEntity> {
  override fun convert(source: FixExpenseTemplateCreate): FixExpenseTemplateEntity {
    return FixExpenseTemplateEntity(
      title = source.title,
      amountInCents = source.amountInCents,
      repeatInterval = repeatIntervalToRepeatIntervalEnumConverter.convert(source.repeatInterval),
      categoryId = source.categoryId
    )
  }
}
