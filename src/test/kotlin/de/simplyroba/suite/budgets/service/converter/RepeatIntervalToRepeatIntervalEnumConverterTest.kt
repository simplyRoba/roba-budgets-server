package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalEnum
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RepeatIntervalToRepeatIntervalEnumConverterTest {

  private val converter = RepeatIntervalToRepeatIntervalEnumConverter()

  @ParameterizedTest
  @CsvSource(
    "ONCE:ONCE",
    "MONTHLY:MONTHLY",
    "QUARTERLY:QUARTERLY",
    "SEMI_ANNUALLY:SEMI_ANNUALLY",
    "ANNUALLY:ANNUALLY",
    delimiter = ':'
  )
  fun `converts repeat interval to entity repeat interval`(
    repeatInterval: RepeatInterval,
    repeatIntervalEnum: RepeatIntervalEnum
  ) {
    assertEquals(repeatIntervalEnum, converter.convert(repeatInterval))
  }
}
