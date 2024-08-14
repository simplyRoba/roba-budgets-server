package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RepeatIntervalPersistenceEnumToDtoConverterTest {

  private val converter = RepeatIntervalPersistenceEnumToDtoConverter()

  @ParameterizedTest
  @CsvSource(
    "MONTHLY:MONTHLY",
    "QUARTERLY:QUARTERLY",
    "SEMI_ANNUALLY:SEMI_ANNUALLY",
    "ANNUALLY:ANNUALLY",
    delimiter = ':'
  )
  fun `converts entity repeat interval to repeat interval`(
    repeatIntervalPersistenceEnum: RepeatIntervalPersistenceEnum,
    repeatInterval: RepeatInterval
  ) {
    assertEquals(repeatInterval, converter.convert(repeatIntervalPersistenceEnum))
  }
}
