package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import java.time.OffsetDateTime
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class IncomeCreateToIncomeEntityConverterTest {

  private val converter = IncomeCreateToIncomeEntityConverter()

  @Test
  fun `should convert create to entity`() {
    val incomeCreate =
      IncomeCreate(title = "Test", amountInCents = 1000, dueDate = OffsetDateTime.now())

    val result = converter.convert(incomeCreate)

    assertThat(result.title).isEqualTo(incomeCreate.title)
    assertThat(result.amountInCents).isEqualTo(incomeCreate.amountInCents)
    assertThat(result.dueDate).isEqualTo(incomeCreate.dueDate)
  }
}
