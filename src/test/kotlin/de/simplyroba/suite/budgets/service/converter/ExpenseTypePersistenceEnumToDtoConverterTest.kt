package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExpenseTypePersistenceEnumToDtoConverterTest {

  private val converter = ExpenseTypePersistenceEnumToDtoConverter()

  @ParameterizedTest
  @CsvSource("FIX:FIX", "FLEX:FLEX", "BUDGET:BUDGET", delimiter = ':')
  fun `should convert entity type to dto type`(
    entityType: ExpenseTypePersistenceEnum,
    dtoType: ExpenseType,
  ) {
    val result = converter.convert(entityType)
    assertThat(result).isEqualTo(dtoType)
  }
}
