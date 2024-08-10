package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.springframework.stereotype.Component

@Component
class ExpenseTypeToExpenseEntityTypeConverter : Converter<ExpenseType, ExpenseEntityType> {
  override fun convert(source: ExpenseType): ExpenseEntityType {
    return when (source) {
      ExpenseType.FIX -> ExpenseEntityType.FIX
      ExpenseType.FLEX -> ExpenseEntityType.FLEX
      ExpenseType.BUDGET -> ExpenseEntityType.BUDGET
    }
  }
}
