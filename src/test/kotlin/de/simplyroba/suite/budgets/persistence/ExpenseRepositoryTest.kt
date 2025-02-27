package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum.FIX
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum.FLEX
import java.time.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class ExpenseRepositoryTest : AbstractIntegrationTest() {

  @Autowired private lateinit var expenseRepository: ExpenseRepository
  @Autowired private lateinit var categoryRepository: CategoryRepository
  @Autowired private lateinit var budgetRepository: BudgetRepository

  private var categoryId: Long = 0

  @BeforeEach
  fun setUp() {
    val category =
      categoryRepository
        .save(CategoryEntity(name = "Default Catgeory", disabled = false, parentCategoryId = null))
        .blockOptional()
        .get()
    categoryId = category.id
  }

  @Test
  fun `should include start and end date`() {

    val startDate = LocalDate.parse("2021-01-01")
    val betweenDate = LocalDate.parse("2021-01-02")
    val endDate = LocalDate.parse("2021-01-03")

    val expenseEqualStartDate =
      ExpenseEntity(
        title = "Expense 1",
        amountInCents = 1000,
        dueDate = startDate,
        type = FLEX,
        categoryId = categoryId,
        budgetId = null,
      )
    val expenseBetweenDate =
      ExpenseEntity(
        title = "Expense 2",
        amountInCents = 1000,
        dueDate = betweenDate,
        type = FLEX,
        categoryId = categoryId,
        budgetId = null,
      )
    val expenseEqualEndDate =
      ExpenseEntity(
        title = "Expense 3",
        amountInCents = 1000,
        dueDate = endDate,
        type = FLEX,
        categoryId = categoryId,
        budgetId = null,
      )

    expenseRepository
      .saveAll(listOf(expenseEqualStartDate, expenseBetweenDate, expenseEqualEndDate))
      .blockLast()

    expenseRepository
      .findAllByDueDateBetween(startDate, endDate)
      .test()
      .expectNextCount(3)
      .verifyComplete()
  }

  @Test
  fun `should find by type`() {
    val flexExpense =
      ExpenseEntity(
        title = "Flex Expense",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = FLEX,
        categoryId = categoryId,
        budgetId = null,
      )
    val otherExpense =
      ExpenseEntity(
        title = "Other Expense",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = FIX,
        categoryId = categoryId,
        budgetId = null,
      )

    expenseRepository.saveAll(listOf(flexExpense, otherExpense)).blockLast()

    expenseRepository.findAllByType(FLEX).test().expectNextCount(1).verifyComplete()
  }

  @Test
  fun `should find by category id`() {
    val searchedCategoryId =
      categoryRepository
        .save(CategoryEntity(name = "Searched Catgeory", disabled = false, parentCategoryId = null))
        .blockOptional()
        .get()
        .id

    val expense1 =
      ExpenseEntity(
        title = "Expense 1",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = FLEX,
        categoryId = searchedCategoryId,
        budgetId = null,
      )
    val expense2 =
      ExpenseEntity(
        title = "Expense 2",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = FLEX,
        categoryId = categoryId,
        budgetId = null,
      )

    expenseRepository.saveAll(listOf(expense1, expense2)).blockLast()

    expenseRepository
      .findAllByCategoryId(searchedCategoryId)
      .test()
      .expectNextCount(1)
      .verifyComplete()
  }

  @Test
  fun `should find by budget id`() {
    val budgetId =
      budgetRepository
        .save(
          BudgetEntity(
            name = "Budget",
            monthlySavingAmountInCents = 99,
            totalSavedAmountInCents = 199,
            categoryId = categoryId,
          )
        )
        .blockOptional()
        .get()
        .id
    val expense1 =
      ExpenseEntity(
        title = "Expense 1",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = FLEX,
        categoryId = categoryId,
        budgetId = budgetId,
      )
    val expense2 =
      ExpenseEntity(
        title = "Expense 2",
        amountInCents = 1000,
        dueDate = LocalDate.now(),
        type = FLEX,
        categoryId = categoryId,
        budgetId = null,
      )

    expenseRepository.saveAll(listOf(expense1, expense2)).blockLast()

    expenseRepository.findAllByBudgetId(budgetId).test().expectNextCount(1).verifyComplete()
  }
}
