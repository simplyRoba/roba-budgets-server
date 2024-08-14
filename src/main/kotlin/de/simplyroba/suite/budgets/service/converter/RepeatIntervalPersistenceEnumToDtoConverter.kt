package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class RepeatIntervalPersistenceEnumToDtoConverter :
  Converter<RepeatIntervalPersistenceEnum, RepeatInterval> {
  override fun convert(source: RepeatIntervalPersistenceEnum): RepeatInterval {
    return when (source) {
      RepeatIntervalPersistenceEnum.MONTHLY -> RepeatInterval.MONTHLY
      RepeatIntervalPersistenceEnum.QUARTERLY -> RepeatInterval.QUARTERLY
      RepeatIntervalPersistenceEnum.SEMI_ANNUALLY -> RepeatInterval.SEMI_ANNUALLY
      RepeatIntervalPersistenceEnum.ANNUALLY -> RepeatInterval.ANNUALLY
    }
  }
}
