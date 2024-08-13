package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class RepeatIntervalEnumToRepeatIntervalConverter : Converter<RepeatIntervalEnum, RepeatInterval> {
  override fun convert(source: RepeatIntervalEnum): RepeatInterval {
    return when (source) {
      RepeatIntervalEnum.ONCE -> RepeatInterval.ONCE
      RepeatIntervalEnum.MONTHLY -> RepeatInterval.MONTHLY
      RepeatIntervalEnum.QUARTERLY -> RepeatInterval.QUARTERLY
      RepeatIntervalEnum.SEMI_ANNUALLY -> RepeatInterval.SEMI_ANNUALLY
      RepeatIntervalEnum.ANNUALLY -> RepeatInterval.ANNUALLY
    }
  }
}
