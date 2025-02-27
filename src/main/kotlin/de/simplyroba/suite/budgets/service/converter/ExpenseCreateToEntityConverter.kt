package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseCreateToEntityConverter(
	private val expenseTypeToPersistenceEnumConverter:
		Converter<ExpenseType, ExpenseTypePersistenceEnum>
) : Converter<ExpenseCreate, ExpenseEntity> {
	override fun convert(source: ExpenseCreate): ExpenseEntity {
		return ExpenseEntity(
			title = source.title,
			amountInCents = source.amountInCents,
			dueDate = source.dueDate,
			type = expenseTypeToPersistenceEnumConverter.convert(source.type),
			categoryId = source.categoryId,
			budgetId = source.budgetId,
		)
	}
}
