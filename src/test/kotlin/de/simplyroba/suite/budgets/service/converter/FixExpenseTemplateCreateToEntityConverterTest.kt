package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateCreate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FixExpenseTemplateCreateToEntityConverterTest {

  private val converter =
    FixExpenseTemplateCreateToEntityConverter(RepeatIntervalToPersistenceEnumConverter())

  @Test
  fun `convert should convert create to entity`() {
    val source =
      FixExpenseTemplateCreate(
        title = "title",
        amountInCents = 100,
        repeatInterval = RepeatInterval.MONTHLY,
        categoryId = 1,
      )

    val result = converter.convert(source)

    assertEquals(source.title, result.title)
    assertEquals(source.amountInCents, result.amountInCents)
    assertEquals(source.categoryId, result.categoryId)
  }
}
