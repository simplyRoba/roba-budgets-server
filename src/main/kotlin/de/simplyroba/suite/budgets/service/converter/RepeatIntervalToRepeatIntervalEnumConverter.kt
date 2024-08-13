package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class RepeatIntervalToRepeatIntervalEnumConverter : Converter<RepeatInterval, RepeatIntervalEnum> {
  override fun convert(source: RepeatInterval): RepeatIntervalEnum {
    return when (source) {
      RepeatInterval.ONCE -> RepeatIntervalEnum.ONCE
      RepeatInterval.MONTHLY -> RepeatIntervalEnum.MONTHLY
      RepeatInterval.QUARTERLY -> RepeatIntervalEnum.QUARTERLY
      RepeatInterval.SEMI_ANNUALLY -> RepeatIntervalEnum.SEMI_ANNUALLY
      RepeatInterval.ANNUALLY -> RepeatIntervalEnum.ANNUALLY
    }
  }
}
