package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import java.time.LocalDate
import org.junit.jupiter.api.Test

class SummaryIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return empty monthly summary if no values present`() {
    val year = 2021
    val month = 1

    val response = webTestClient.get().uri("/api/v1/summary/year/$year/month/$month").exchange()

    response
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.month")
      .isEqualTo(month)
      .jsonPath("$.year")
      .isEqualTo(year)
      .jsonPath("$.totalIncomeInCents")
      .isEqualTo(0)
      .jsonPath("$.totalFixExpensesInCents")
      .isEqualTo(0)
      .jsonPath("$.totalFlexExpensesInCents")
      .isEqualTo(0)
  }

  @Test
  fun `should return monthly summary`() {
    val year = 2021
    val month = 1
    val dueDate = LocalDate.of(year, month, 1)
    val categoryId = createCategory("Default Category").id
    val incomeSum = 10000
    val fixExpenseSum = 100
    val flexExpenseSum = 100
    createIncome(amountInCents = incomeSum, dueDate = dueDate)
    createExpense(
      amountInCents = fixExpenseSum,
      dueDate = dueDate,
      categoryId = categoryId,
      type = ExpenseTypePersistenceEnum.FIX
    )
    createExpense(
      amountInCents = flexExpenseSum,
      dueDate = dueDate,
      categoryId = categoryId,
      type = ExpenseTypePersistenceEnum.FLEX
    )

    val response = webTestClient.get().uri("/api/v1/summary/year/$year/month/$month").exchange()

    response
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.month")
      .isEqualTo(month)
      .jsonPath("$.year")
      .isEqualTo(year)
      .jsonPath("$.totalIncomeInCents")
      .isEqualTo(incomeSum)
      .jsonPath("$.totalFixExpensesInCents")
      .isEqualTo(fixExpenseSum)
      .jsonPath("$.totalFlexExpensesInCents")
      .isEqualTo(flexExpenseSum)
  }

  @Test
  fun `should return budget summary`() {
    val year = 2021
    val month = 1
    val dueDate = LocalDate.of(year, month, 1)
    val categoryId = createCategory("Default Category").id
    val totalSavedAmount = 10000
    val budgetExpenseSum = 500
    val budgetId = createBudget("Budget", 1000, totalSavedAmount, categoryId).id
    createBudgetExpense(
      amountInCents = budgetExpenseSum,
      dueDate = dueDate,
      budgetId = budgetId,
      categoryId = categoryId
    )

    val response = webTestClient.get().uri("/api/v1/summary/budgets").exchange()

    response
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$")
      .isArray
      .jsonPath("$.[0].id")
      .isEqualTo(budgetId)
      .jsonPath("$.[0].name")
      .isEqualTo("Budget")
      .jsonPath("$.[0].totalSavedAmountInCents")
      .isEqualTo(totalSavedAmount)
      .jsonPath("$.[0].totalExpensesInCents")
      .isEqualTo(budgetExpenseSum)
  }
}
