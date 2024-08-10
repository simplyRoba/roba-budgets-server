package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BudgetCreateToBudgetEntityConverterTest {

  private val converter = BudgetCreateToBudgetEntityConverter()

  @Test
  fun `should convert create to entity`() {
    val budgetCreate = BudgetCreate(name = "Test Budget", savingAmountInCents = 1000)

    val result = converter.convert(budgetCreate)

    assertThat(result.name).isEqualTo(budgetCreate.name)
    assertThat(result.savingAmountInCents).isEqualTo(budgetCreate.savingAmountInCents)
  }
}
