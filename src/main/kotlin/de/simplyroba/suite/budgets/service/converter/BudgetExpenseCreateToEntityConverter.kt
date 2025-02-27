package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetExpenseEntity
import de.simplyroba.suite.budgets.rest.model.BudgetExpenseCreate
import org.springframework.stereotype.Component

@Component
class BudgetExpenseCreateToEntityConverter : Converter<BudgetExpenseCreate, BudgetExpenseEntity> {

  override fun convert(source: BudgetExpenseCreate): BudgetExpenseEntity {
    return BudgetExpenseEntity(
      title = source.title,
      amountInCents = source.amountInCents,
      dueDate = source.dueDate,
      categoryId = source.categoryId,
      budgetId = source.budgetId,
    )
  }
}
