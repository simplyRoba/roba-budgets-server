package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import java.time.LocalDate
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class IncomeCreateToEntityConverterTest {

  private val converter = IncomeCreateToEntityConverter()

  @Test
  fun `should convert create to entity`() {
    val incomeCreate = IncomeCreate(title = "Test", amountInCents = 1000, dueDate = LocalDate.now())

    val result = converter.convert(incomeCreate)

    assertThat(result.title).isEqualTo(incomeCreate.title)
    assertThat(result.amountInCents).isEqualTo(incomeCreate.amountInCents)
    assertThat(result.dueDate).isEqualTo(incomeCreate.dueDate)
  }
}
