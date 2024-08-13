package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.EntityRepeatInterval
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class EntityRepeatIntervalToRepeatIntervalConverter :
  Converter<EntityRepeatInterval, RepeatInterval> {
  override fun convert(source: EntityRepeatInterval): RepeatInterval {
    return when (source) {
      EntityRepeatInterval.MONTHLY -> RepeatInterval.MONTHLY
      EntityRepeatInterval.QUARTERLY -> RepeatInterval.QUARTERLY
      EntityRepeatInterval.SEMI_ANNUALLY -> RepeatInterval.SEMI_ANNUALLY
      EntityRepeatInterval.ANNUALLY -> RepeatInterval.ANNUALLY
    }
  }
}
