package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.rest.model.BudgetExpense
import de.simplyroba.suite.budgets.rest.model.BudgetExpenseCreate
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.expectBody

class BudgetExpenseIntegrationTest : AbstractIntegrationTest() {

	@Test
	fun `should return budget expense list`() {
		val categoryId = createCategory(name = "Default Category").id
		val budgetId = createBudget(name = "Budget", categoryId = categoryId).id
		val size = 3

		(1..size).forEach { i ->
			createBudgetExpense(name = "Budget Expense $i", budgetId = budgetId, categoryId = categoryId)
		}

		webTestClient
			.get()
			.uri("/api/v1/budget-expense")
			.exchange()
			.expectStatus()
			.isOk
			.expectBody()
			.jsonPath("$.length()")
			.isEqualTo(size)
	}

	@Test
	fun `should return budget expense by id`() {
		val categoryId = createCategory(name = "Default Category").id
		val budgetId = createBudget(name = "Budget", categoryId = categoryId).id
		val name = "Budget Expense"
		val id = createBudgetExpense(name, categoryId = categoryId, budgetId = budgetId).id

		webTestClient
			.get()
			.uri("/api/v1/budget-expense/$id")
			.exchange()
			.expectStatus()
			.isOk
			.expectBody<BudgetExpense>()
			.consumeWith {
				assertThat(it.responseBody?.id).isEqualTo(id)
				assertThat(it.responseBody?.title).isEqualTo(name)
			}
	}

	@Test
	fun `should return 404 when budget expense not found on get`() {
		val id = 1

		webTestClient.get().uri("/api/v1/budget-expense/$id").exchange().expectStatus().isNotFound
	}

	@Test
	fun `should create budget expense`() {
		val categoryId = createCategory(name = "Default Category").id
		val budgetId = createBudget(name = "Budget", categoryId = categoryId).id
		val title = "Budget Expense"
		val amountInCents = 1000
		val dueDate = LocalDate.now()

		webTestClient
			.post()
			.uri("/api/v1/budget-expense")
			.bodyValue(
				BudgetExpenseCreate(
					title = title,
					amountInCents = amountInCents,
					dueDate = dueDate,
					categoryId = categoryId,
					budgetId = budgetId,
				)
			)
			.exchange()
			.expectStatus()
			.isCreated
			.expectBody<BudgetExpense>()
			.consumeWith {
				assertThat(it.responseBody?.title).isEqualTo(title)
				assertThat(it.responseBody?.amountInCents).isEqualTo(amountInCents)
				assertThat(it.responseBody?.dueDate).isEqualTo(dueDate)
				assertThat(it.responseBody?.categoryId).isEqualTo(categoryId)
				assertThat(it.responseBody?.budgetId).isEqualTo(budgetId)
			}
	}

	@Test
	fun `should update budget expense`() {
		val categoryId = createCategory(name = "Default Category").id
		val budgetId = createBudget(name = "Budget", categoryId = categoryId).id
		val id =
			createBudgetExpense(name = "Budget Expense", categoryId = categoryId, budgetId = budgetId).id
		val newTitle = "Budget Expense Updated"
		val newAmountInCents = 999
		val newDueDate = LocalDate.now().plusDays(1)
		val newCategoryId = createCategory(name = "New Category").id

		webTestClient
			.put()
			.uri("/api/v1/budget-expense/$id")
			.bodyValue(
				BudgetExpenseCreate(
					title = newTitle,
					amountInCents = newAmountInCents,
					dueDate = newDueDate,
					categoryId = newCategoryId,
					budgetId = budgetId,
				)
			)
			.exchange()
			.expectStatus()
			.isOk
			.expectBody<BudgetExpense>()
			.consumeWith {
				assertThat(it.responseBody?.id).isEqualTo(id)
				assertThat(it.responseBody?.title).isEqualTo(newTitle)
				assertThat(it.responseBody?.amountInCents).isEqualTo(newAmountInCents)
				assertThat(it.responseBody?.dueDate).isEqualTo(newDueDate)
				assertThat(it.responseBody?.categoryId).isEqualTo(newCategoryId)
			}
	}

	@Test
	fun `should return 404 when budget expense not found on update`() {
		val id = 1

		webTestClient
			.put()
			.uri("/api/v1/budget-expense/$id")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(BudgetExpenseCreate("Name", 99, LocalDate.now(), 1, 1))
			.exchange()
			.expectStatus()
			.isNotFound
	}

	@Test
	fun `should delete budget expense`() {
		val categoryId = createCategory(name = "Default Category").id
		val budgetId = createBudget(name = "Budget", categoryId = categoryId).id
		val id =
			createBudgetExpense(name = "Budget Expense", categoryId = categoryId, budgetId = budgetId).id

		webTestClient.delete().uri("/api/v1/budget-expense/$id").exchange().expectStatus().isNoContent
	}
}
