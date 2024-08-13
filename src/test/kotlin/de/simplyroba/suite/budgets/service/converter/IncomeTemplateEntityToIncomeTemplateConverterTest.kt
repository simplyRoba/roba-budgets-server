package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.EntityRepeatInterval
import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IncomeTemplateEntityToIncomeTemplateConverterTest {

  private val converter =
    IncomeTemplateEntityToIncomeTemplateConverter(EntityRepeatIntervalToRepeatIntervalConverter())

  @Test
  fun `should convert entity to dto`() {
    val entity =
      IncomeTemplateEntity(
        id = 1,
        title = "title",
        amountInCents = 100,
        repeatInterval = EntityRepeatInterval.ANNUALLY
      )

    val result = converter.convert(entity)

    assertThat(result.id).isEqualTo(entity.id)
    assertThat(result.title).isEqualTo(entity.title)
    assertThat(result.amountInCents).isEqualTo(entity.amountInCents)
  }
}
