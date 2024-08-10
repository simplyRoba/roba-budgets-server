package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseEntityToExpenseConverter(
  private val expenseEntityTypeToExpenseTypeConverter: Converter<ExpenseEntityType, ExpenseType>
) : Converter<ExpenseEntity, Expense> {
  override fun convert(source: ExpenseEntity): Expense {
    return Expense(
      id = source.id,
      title = source.title,
      amountInCents = source.amountInCents,
      dueDate = source.dueDate,
      type = expenseEntityTypeToExpenseTypeConverter.convert(source.type),
      categoryId = source.categoryId,
      budgetId = source.budgetId
    )
  }
}
