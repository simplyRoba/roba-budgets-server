package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.BudgetExpenseCreate
import java.time.LocalDate
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class BudgetExpenseCreateToEntityConverterTest {

	private val converter = BudgetExpenseCreateToEntityConverter()

	@Test
	fun `should convert create to entity`() {
		val budgetExpenseCreate = BudgetExpenseCreate("Test Budget Expense", 100, LocalDate.now(), 1, 1)

		val result = converter.convert(budgetExpenseCreate)

		assertThat(result.title).isEqualTo(budgetExpenseCreate.title)
		assertThat(result.amountInCents).isEqualTo(budgetExpenseCreate.amountInCents)
		assertThat(result.dueDate).isEqualTo(budgetExpenseCreate.dueDate)
		assertThat(result.categoryId).isEqualTo(budgetExpenseCreate.categoryId)
		assertThat(result.budgetId).isEqualTo(budgetExpenseCreate.budgetId)
	}
}
