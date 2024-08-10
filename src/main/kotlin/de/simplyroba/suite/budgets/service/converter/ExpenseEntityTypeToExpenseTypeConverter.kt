package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseEntityTypeToExpenseTypeConverter : Converter<ExpenseEntityType, ExpenseType> {
  override fun convert(source: ExpenseEntityType): ExpenseType {
    return when (source) {
      ExpenseEntityType.FIX -> ExpenseType.FIX
      ExpenseEntityType.FLEX -> ExpenseType.FLEX
      ExpenseEntityType.BUDGET -> ExpenseType.BUDGET
    }
  }
}
