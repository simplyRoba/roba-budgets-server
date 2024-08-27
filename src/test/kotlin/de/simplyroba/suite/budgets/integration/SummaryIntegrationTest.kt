package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import java.time.LocalDate
import org.junit.jupiter.api.Test

class SummaryIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return empty summary if no values present`() {
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
      .jsonPath("$.budgets")
      .isEmpty
  }

  @Test
  fun `should return summary with income and expenses`() {
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
      .jsonPath("$.budgets")
      .isEmpty
  }

  @Test
  fun `should return summary with budgets`() {
    val year = 2021
    val month = 1
    val dueDate = LocalDate.of(year, month, 1)
    val categoryId = createCategory("Default Category").id
    val totalSavedAmount = 10000
    val budgetExpenseSum = 500
    val budgetId = createBudget("Budget", 1000, totalSavedAmount, categoryId).id
    createBudgetExpense(amountInCents = budgetExpenseSum, dueDate = dueDate, budgetId = budgetId, categoryId = categoryId)

    val response = webTestClient.get().uri("/api/v1/summary/year/$year/month/$month").exchange()

    response
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.budgets")
      .isArray
      .jsonPath("$.budgets[0].name")
      .isEqualTo("Budget")
      .jsonPath("$.budgets[0].totalSavedAmountInCents")
      .isEqualTo(totalSavedAmount)
      .jsonPath("$.budgets[0].totalExpensesInCents")
      .isEqualTo(budgetExpenseSum)
  }
}
