package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IncomeEntityToDtoConverterTest {

  private val converter = IncomeEntityToDtoConverter()

  @Test
  fun `should convert entity to dto`() {
    val entity =
      IncomeEntity(id = 1, title = "title", amountInCents = 100, dueDate = LocalDate.now())

    val result = converter.convert(entity)

    assertThat(result.id).isEqualTo(entity.id)
    assertThat(result.title).isEqualTo(entity.title)
    assertThat(result.amountInCents).isEqualTo(entity.amountInCents)
    assertThat(result.dueDate).isEqualTo(entity.dueDate)
  }
}
