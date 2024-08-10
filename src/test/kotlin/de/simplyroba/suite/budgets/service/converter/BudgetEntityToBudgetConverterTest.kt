package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BudgetEntityToBudgetConverterTest {

  private val converter = BudgetEntityToBudgetConverter()

  @Test
  fun `should convert entity to dto`() {
    val entity = BudgetEntity(id = 1, name = "name", savingAmountInCents = 2)

    val dto = converter.convert(entity)

    assertEquals(entity.id, dto.id)
    assertEquals(entity.name, dto.name)
    assertEquals(entity.savingAmountInCents, dto.savingAmountInCents)
  }
}
