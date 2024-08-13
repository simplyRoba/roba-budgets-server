package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.IncomeTemplateCreate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IncomeTemplateCreateToEntityConverterTest {

  private val converter =
    IncomeTemplateCreateToEntityConverter(RepeatIntervalToEntityRepeatIntervalConverter())

  @Test
  fun `should convert create to entity`() {
    val incomeTemplateCreate =
      IncomeTemplateCreate(
        title = "Test",
        amountInCents = 1000,
        repeatInterval = RepeatInterval.MONTHLY
      )

    val result = converter.convert(incomeTemplateCreate)

    assertEquals(incomeTemplateCreate.title, result.title)
    assertEquals(incomeTemplateCreate.amountInCents, result.amountInCents)
  }
}
