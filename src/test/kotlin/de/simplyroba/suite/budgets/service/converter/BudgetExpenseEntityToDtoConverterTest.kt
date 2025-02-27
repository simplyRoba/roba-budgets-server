package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.BudgetExpenseEntity
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BudgetExpenseEntityToDtoConverterTest {

	private val converter = BudgetExpenseEntityToDtoConverter()

	@Test
	fun `should convert entity to dto`() {
		val entity = BudgetExpenseEntity(1, "name", 20, LocalDate.now(), 100, 2)

		val result = converter.convert(entity)

		assertThat(result.id).isEqualTo(entity.id)
		assertThat(result.title).isEqualTo(entity.title)
		assertThat(result.amountInCents).isEqualTo(entity.amountInCents)
		assertThat(result.dueDate).isEqualTo(entity.dueDate)
		assertThat(result.categoryId).isEqualTo(entity.categoryId)
		assertThat(result.budgetId).isEqualTo(entity.budgetId)
	}
}
