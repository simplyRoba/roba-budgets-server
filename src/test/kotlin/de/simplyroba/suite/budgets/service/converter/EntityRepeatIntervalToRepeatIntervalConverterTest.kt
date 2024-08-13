package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.EntityRepeatInterval
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class EntityRepeatIntervalToRepeatIntervalConverterTest {

  private val converter = EntityRepeatIntervalToRepeatIntervalConverter()

  @ParameterizedTest
  @CsvSource(
    "MONTHLY:MONTHLY",
    "QUARTERLY:QUARTERLY",
    "SEMI_ANNUALLY:SEMI_ANNUALLY",
    "ANNUALLY:ANNUALLY",
    delimiter = ':'
  )
  fun `converts entity repeat interval to repeat interval`(
    entityRepeatInterval: EntityRepeatInterval,
    repeatInterval: RepeatInterval
  ) {
    assertEquals(repeatInterval, converter.convert(entityRepeatInterval))
  }
}
