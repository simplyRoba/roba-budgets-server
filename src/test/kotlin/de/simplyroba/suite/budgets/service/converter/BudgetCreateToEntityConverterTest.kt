package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BudgetCreateToEntityConverterTest {

	private val converter = BudgetCreateToEntityConverter()

	@Test
	fun `should convert create to entity`() {

		val budgetCreate = BudgetCreate("Test Budget", 100, 999, 1)

		val result = converter.convert(budgetCreate)

		assertThat(result.name).isEqualTo(budgetCreate.name)
		assertThat(result.monthlySavingAmountInCents).isEqualTo(budgetCreate.monthlySavingAmountInCents)
		assertThat(result.totalSavedAmountInCents).isEqualTo(budgetCreate.totalSavedAmountInCents)
		assertThat(result.categoryId).isEqualTo(budgetCreate.categoryId)
	}
}
