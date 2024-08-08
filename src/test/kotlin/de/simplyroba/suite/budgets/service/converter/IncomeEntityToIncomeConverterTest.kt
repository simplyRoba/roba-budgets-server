package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import java.time.OffsetDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IncomeEntityToIncomeConverterTest {

  private val converter = IncomeEntityToIncomeConverter()

  @Test
  fun `should convert entity to dto`() {
    val entity =
      IncomeEntity(id = 1, title = "title", amountInCents = 100, dueDate = OffsetDateTime.now())

    val dto = converter.convert(entity)

    assertThat(entity.id).isEqualTo(dto.id)
    assertThat(entity.title).isEqualTo(dto.title)
    assertThat(entity.amountInCents).isEqualTo(dto.amountInCents)
    assertThat(entity.dueDate).isEqualTo(dto.dueDate)
  }
}
