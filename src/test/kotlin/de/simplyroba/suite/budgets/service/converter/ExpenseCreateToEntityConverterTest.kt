package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExpenseCreateToEntityConverterTest {

  private val converter = ExpenseCreateToEntityConverter(ExpenseTypeToPersistenceEnumConverter())

  @Test
  fun `should convert create to entity`() {
    val expenseCreate =
      ExpenseCreate(
        title = "Test",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = ExpenseType.FIX,
        categoryId = 1,
        budgetId = 1
      )

    val result = converter.convert(expenseCreate)

    assertEquals(expenseCreate.title, result.title)
    assertEquals(expenseCreate.amountInCents, result.amountInCents)
    assertEquals(expenseCreate.dueDate, result.dueDate)
    assertEquals(expenseCreate.categoryId, result.categoryId)
    assertEquals(expenseCreate.budgetId, result.budgetId)
  }
}
