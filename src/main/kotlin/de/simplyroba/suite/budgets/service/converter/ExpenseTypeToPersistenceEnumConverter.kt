package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseTypeToPersistenceEnumConverter : Converter<ExpenseType, ExpenseTypePersistenceEnum> {
	override fun convert(source: ExpenseType): ExpenseTypePersistenceEnum {
		return when (source) {
			ExpenseType.FIX -> ExpenseTypePersistenceEnum.FIX
			ExpenseType.FLEX -> ExpenseTypePersistenceEnum.FLEX
			ExpenseType.BUDGET -> ExpenseTypePersistenceEnum.BUDGET
		}
	}
}
