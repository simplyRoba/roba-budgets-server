package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetExpenseEntity
import de.simplyroba.suite.budgets.rest.model.BudgetExpense
import org.springframework.stereotype.Component

@Component
class BudgetExpenseEntityToDtoConverter : Converter<BudgetExpenseEntity, BudgetExpense> {

	override fun convert(source: BudgetExpenseEntity): BudgetExpense {
		return BudgetExpense(
			id = source.id,
			title = source.title,
			amountInCents = source.amountInCents,
			dueDate = source.dueDate,
			categoryId = source.categoryId,
			budgetId = source.budgetId,
		)
	}
}
