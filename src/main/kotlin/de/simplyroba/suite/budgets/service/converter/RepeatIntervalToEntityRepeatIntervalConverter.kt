package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.EntityRepeatInterval
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class RepeatIntervalToEntityRepeatIntervalConverter :
  Converter<RepeatInterval, EntityRepeatInterval> {
  override fun convert(source: RepeatInterval): EntityRepeatInterval {
    return when (source) {
      RepeatInterval.MONTHLY -> EntityRepeatInterval.MONTHLY
      RepeatInterval.QUARTERLY -> EntityRepeatInterval.QUARTERLY
      RepeatInterval.SEMI_ANNUALLY -> EntityRepeatInterval.SEMI_ANNUALLY
      RepeatInterval.ANNUALLY -> EntityRepeatInterval.ANNUALLY
    }
  }
}
