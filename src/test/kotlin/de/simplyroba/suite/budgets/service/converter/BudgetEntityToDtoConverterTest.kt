package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BudgetEntityToDtoConverterTest {

	private val converter = BudgetEntityToDtoConverter()

	@Test
	fun `should convert entity to dto`() {
		val entity = BudgetEntity(1, "name", 20, 100, 2)

		val result = converter.convert(entity)

		assertThat(result.id).isEqualTo(entity.id)
		assertThat(result.name).isEqualTo(entity.name)
		assertThat(result.monthlySavingAmountInCents).isEqualTo(entity.monthlySavingAmountInCents)
		assertThat(result.totalSavedAmountInCents).isEqualTo(entity.totalSavedAmountInCents)
		assertThat(result.categoryId).isEqualTo(entity.categoryId)
	}
}
