package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExpenseTypeToExpenseEntityTypeConverterTest {

  private val converter = ExpenseTypeToExpenseEntityTypeConverter()

  @ParameterizedTest
  @CsvSource("FIX, FIX", "FLEX, FLEX", "BUDGET, BUDGET")
  fun `should convert dto type to entity type`(
    dtoType: ExpenseType,
    entityType: ExpenseEntityType
  ) {
    val result = converter.convert(dtoType)
    assertThat(result).isEqualTo(entityType)
  }
}
