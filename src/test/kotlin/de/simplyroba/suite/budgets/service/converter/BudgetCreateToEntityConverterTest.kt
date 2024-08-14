package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BudgetCreateToEntityConverterTest {

  private val converter = BudgetCreateToEntityConverter()

  @Test
  fun `should convert create to entity`() {

    val budgetCreate =
      BudgetCreate(title = "Test Budget", savingAmountInCents = 1000, categoryId = 1)

    val result = converter.convert(budgetCreate)

    assertThat(result.title).isEqualTo(budgetCreate.title)
    assertThat(result.savingAmountInCents).isEqualTo(budgetCreate.savingAmountInCents)
    assertThat(result.categoryId).isEqualTo(budgetCreate.categoryId)
  }
}
