package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RepeatIntervalToPersistenceEnumConverterTest {

  private val converter = RepeatIntervalToPersistenceEnumConverter()

  @ParameterizedTest
  @CsvSource(
    "MONTHLY:MONTHLY",
    "QUARTERLY:QUARTERLY",
    "SEMI_ANNUALLY:SEMI_ANNUALLY",
    "ANNUALLY:ANNUALLY",
    delimiter = ':'
  )
  fun `converts repeat interval to entity repeat interval`(
    repeatInterval: RepeatInterval,
    repeatIntervalPersistenceEnum: RepeatIntervalPersistenceEnum
  ) {
    assertEquals(repeatIntervalPersistenceEnum, converter.convert(repeatInterval))
  }
}
