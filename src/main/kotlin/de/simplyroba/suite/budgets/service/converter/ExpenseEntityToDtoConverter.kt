package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseEntityToDtoConverter(
  private val expenseTypePersistenceEnumToDtoConverter:
    Converter<ExpenseTypePersistenceEnum, ExpenseType>
) : Converter<ExpenseEntity, Expense> {
  override fun convert(source: ExpenseEntity): Expense {
    return Expense(
      id = source.id,
      title = source.title,
      amountInCents = source.amountInCents,
      dueDate = source.dueDate,
      type = expenseTypePersistenceEnumToDtoConverter.convert(source.type),
      categoryId = source.categoryId,
      budgetId = source.budgetId
    )
  }
}
