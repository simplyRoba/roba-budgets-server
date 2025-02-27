package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseTypePersistenceEnumToDtoConverter :
	Converter<ExpenseTypePersistenceEnum, ExpenseType> {
	override fun convert(source: ExpenseTypePersistenceEnum): ExpenseType {
		return when (source) {
			ExpenseTypePersistenceEnum.FIX -> ExpenseType.FIX
			ExpenseTypePersistenceEnum.FLEX -> ExpenseType.FLEX
			ExpenseTypePersistenceEnum.BUDGET -> ExpenseType.BUDGET
		}
	}
}
