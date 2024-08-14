package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExpenseTypeToPersistenceEnumConverterTest {

  private val converter = ExpenseTypeToPersistenceEnumConverter()

  @ParameterizedTest
  @CsvSource("FIX:FIX", "FLEX:FLEX", "BUDGET:BUDGET", delimiter = ':')
  fun `should convert dto type to entity type`(
    dtoType: ExpenseType,
    entityType: ExpenseTypePersistenceEnum
  ) {
    val result = converter.convert(dtoType)
    assertThat(result).isEqualTo(entityType)
  }
}
