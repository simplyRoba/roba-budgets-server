package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BudgetEntityToBudgetConverterTest {

  private val converter = BudgetEntityToBudgetConverter()

  @Test
  fun `should convert entity to dto`() {
    val entity = BudgetEntity(id = 1, name = "name", savingAmountInCents = 2)

    val result = converter.convert(entity)

    assertThat(result.id).isEqualTo(entity.id)
    assertThat(result.name).isEqualTo(entity.name)
    assertThat(result.savingAmountInCents).isEqualTo(entity.savingAmountInCents)
  }
}
