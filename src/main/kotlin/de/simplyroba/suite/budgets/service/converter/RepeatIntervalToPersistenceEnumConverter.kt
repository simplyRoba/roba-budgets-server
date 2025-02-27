package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.springframework.stereotype.Component

@Component
class RepeatIntervalToPersistenceEnumConverter :
	Converter<RepeatInterval, RepeatIntervalPersistenceEnum> {
	override fun convert(source: RepeatInterval): RepeatIntervalPersistenceEnum {
		return when (source) {
			RepeatInterval.MONTHLY -> RepeatIntervalPersistenceEnum.MONTHLY
			RepeatInterval.QUARTERLY -> RepeatIntervalPersistenceEnum.QUARTERLY
			RepeatInterval.SEMI_ANNUALLY -> RepeatIntervalPersistenceEnum.SEMI_ANNUALLY
			RepeatInterval.ANNUALLY -> RepeatIntervalPersistenceEnum.ANNUALLY
		}
	}
}
