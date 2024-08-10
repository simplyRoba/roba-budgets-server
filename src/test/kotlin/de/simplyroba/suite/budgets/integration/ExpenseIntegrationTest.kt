package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntityType
import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import java.time.OffsetDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

class ExpenseIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return expense list`() {
    val size = 3
    val categoryId = createCategory(name = "Default Category").id

    (1..size).forEach { i -> createExpense(title = "Expense $i", categoryId = categoryId) }

    webTestClient
      .get()
      .uri("/api/v1/expense")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(size)
  }

  @Test
  fun `should return expense list between dates`() {
    val startDate = OffsetDateTime.now()
    val endDate = startDate.plusDays(1)
    val outsideDate = endDate.plusDays(1)
    val categoryId = createCategory(name = "Default Category").id

    createExpense(title = "Expense start", dueDate = startDate, categoryId = categoryId)
    createExpense(title = "Expense end", dueDate = endDate, categoryId = categoryId)
    createExpense(title = "Expense outside", dueDate = outsideDate, categoryId = categoryId)

    webTestClient
      .get()
      .uri { builder ->
        builder
          .path("/api/v1/expense")
          .queryParam("startDate", "{startDate}")
          .queryParam("endDate", "{endDate}")
          .build(startDate, endDate)
      }
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(2)
  }

  @Test
  fun `should return expense by type`() {
    val categoryId = createCategory(name = "Default Category").id
    val type = ExpenseEntityType.FIX

    createExpense(title = "Expense FIX", categoryId = categoryId, type = type)
    createExpense(title = "Expense FLEX", categoryId = categoryId, type = ExpenseEntityType.FLEX)

    webTestClient
      .get()
      .uri { builder -> builder.path("/api/v1/expense/type/{type}").build(type) }
      .exchange()
      .expectStatus()
      .isOk
      .expectBodyList<Expense>()
      .hasSize(1)
  }

  @Test
  fun `should return expense by type between dates`() {
    val startDate = OffsetDateTime.now()
    val endDate = startDate.plusDays(1)
    val outsideDate = endDate.plusDays(1)
    val categoryId = createCategory(name = "Default Category").id
    val type = ExpenseEntityType.FIX

    createExpense(
      title = "Expense FIX start",
      dueDate = startDate,
      categoryId = categoryId,
      type = type
    )
    createExpense(
      title = "Expense FIX end",
      dueDate = endDate,
      categoryId = categoryId,
      type = type
    )
    createExpense(
      title = "Expense FIX outside",
      dueDate = outsideDate,
      categoryId = categoryId,
      type = type
    )
    createExpense(
      title = "Expense FLEX start",
      dueDate = startDate,
      categoryId = categoryId,
      type = ExpenseEntityType.FLEX
    )

    webTestClient
      .get()
      .uri { builder ->
        builder
          .path("/api/v1/expense/type/{type}")
          .queryParam("startDate", "{startDate}")
          .queryParam("endDate", "{endDate}")
          .build(type, startDate, endDate)
      }
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(2)
  }

  @Test
  fun `should return expense by category`() {
    val categoryId = createCategory(name = "Default Category").id
    val otherCategoryId = createCategory(name = "Other Category").id

    createExpense(title = "Expense 1", categoryId = categoryId)
    createExpense(title = "Expense 2", categoryId = otherCategoryId)

    webTestClient
      .get()
      .uri { builder -> builder.path("/api/v1/expense/category/{categoryId}").build(categoryId) }
      .exchange()
      .expectStatus()
      .isOk
      .expectBodyList<Expense>()
      .hasSize(1)
  }

  @Test
  fun `should return expense by category between dates`() {
    val startDate = OffsetDateTime.now()
    val endDate = startDate.plusDays(1)
    val outsideDate = endDate.plusDays(1)
    val categoryId = createCategory(name = "Default Category").id
    val otherCategoryId = createCategory(name = "Other Category").id

    createExpense(title = "Expense default start", dueDate = startDate, categoryId = categoryId)
    createExpense(title = "Expense default end", dueDate = endDate, categoryId = categoryId)
    createExpense(title = "Expense default outside", dueDate = outsideDate, categoryId = categoryId)
    createExpense(title = "Expense other start", dueDate = startDate, categoryId = otherCategoryId)

    webTestClient
      .get()
      .uri { builder ->
        builder
          .path("/api/v1/expense/category/{categoryId}")
          .queryParam("startDate", "{startDate}")
          .queryParam("endDate", "{endDate}")
          .build(categoryId, startDate, endDate)
      }
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(2)
  }

  @Test
  fun `should return expense by budget`() {
    val categoryId = createCategory(name = "Default Category").id
    val budgetId = createBudget(name = "Default Budget").id
    val otherBudgetId = createBudget(name = "Other Budget").id

    createExpense(title = "Expense 1", budgetId = budgetId, categoryId = categoryId)
    createExpense(title = "Expense 2", budgetId = otherBudgetId, categoryId = categoryId)

    webTestClient
      .get()
      .uri { builder -> builder.path("/api/v1/expense/budget/{budgetId}").build(budgetId) }
      .exchange()
      .expectStatus()
      .isOk
      .expectBodyList<Expense>()
      .hasSize(1)
  }

  @Test
  fun `should return expense by budget between dates`() {
    val startDate = OffsetDateTime.now()
    val endDate = startDate.plusDays(1)
    val outsideDate = endDate.plusDays(1)
    val categoryId = createCategory(name = "Default Category").id
    val budgetId = createBudget(name = "Default Budget").id
    val otherBudgetId = createBudget(name = "Other Budget").id

    createExpense(
      title = "Expense default start",
      dueDate = startDate,
      categoryId = categoryId,
      budgetId = budgetId
    )
    createExpense(
      title = "Expense default end",
      dueDate = endDate,
      categoryId = categoryId,
      budgetId = budgetId
    )
    createExpense(
      title = "Expense default outside",
      dueDate = outsideDate,
      categoryId = categoryId,
      budgetId = budgetId
    )
    createExpense(
      title = "Expense other start",
      dueDate = startDate,
      categoryId = categoryId,
      budgetId = otherBudgetId
    )

    webTestClient
      .get()
      .uri { builder ->
        builder
          .path("/api/v1/expense/budget/{budgetId}")
          .queryParam("startDate", "{startDate}")
          .queryParam("endDate", "{endDate}")
          .build(budgetId, startDate, endDate)
      }
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(2)
  }

  @Test
  fun `should return expense by id`() {
    val categoryId = createCategory(name = "Default Category").id

    val title = "Expense"
    val id = createExpense(title = title, categoryId = categoryId).id

    webTestClient
      .get()
      .uri("/api/v1/expense/$id")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Expense>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(title)
      }
  }

  @Test
  fun `should return 404 when expense not found on get`() {
    val id = 1

    webTestClient.get().uri("/api/v1/expense/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create expense`() {
    val categoryId = createCategory(name = "Default Category").id
    val title = "Expense"
    val amountInCents = 999
    val dueDate = OffsetDateTime.now()
    val type = ExpenseType.FLEX

    webTestClient
      .post()
      .uri("/api/v1/expense")
      .bodyValue(
        ExpenseCreate(
          title = title,
          amountInCents = amountInCents,
          dueDate = dueDate,
          type = type,
          categoryId = categoryId,
          budgetId = null,
        )
      )
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<Expense>()
      .consumeWith {
        assertThat(it.responseBody?.title).isEqualTo(title)
        assertThat(it.responseBody?.amountInCents).isEqualTo(amountInCents)
        assertThat(it.responseBody?.dueDate).isAtSameInstantAs(dueDate)
        assertThat(it.responseBody?.type?.name).isEqualTo(type.name)
        assertThat(it.responseBody?.categoryId).isEqualTo(categoryId)
      }
  }

  @Test
  fun `should update expense`() {
    val categoryId = createCategory(name = "Default Category").id
    val title = "Expense"
    val id = createExpense(title = title, categoryId = categoryId).id
    val newTitle = "New Expense"
    val newAmountInCents = 1000
    val newDueDate = OffsetDateTime.now()
    val newType = ExpenseType.FIX
    val newCategoryId = createCategory(name = "New Category").id

    webTestClient
      .put()
      .uri("/api/v1/expense/$id")
      .bodyValue(
        ExpenseCreate(
          title = newTitle,
          amountInCents = newAmountInCents,
          dueDate = newDueDate,
          type = newType,
          categoryId = newCategoryId,
          budgetId = null,
        )
      )
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Expense>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(newTitle)
        assertThat(it.responseBody?.amountInCents).isEqualTo(newAmountInCents)
        assertThat(it.responseBody?.dueDate).isAtSameInstantAs(newDueDate)
        assertThat(it.responseBody?.type?.name).isEqualTo(newType.name)
        assertThat(it.responseBody?.categoryId).isEqualTo(newCategoryId)
      }
  }

  @Test
  fun `should return 404 when expense not found on update`() {
    val id = 1

    webTestClient
      .put()
      .uri("/api/v1/expense/$id")
      .bodyValue(ExpenseCreate("Title", 999, OffsetDateTime.now(), ExpenseType.FLEX, 1, null))
      .exchange()
      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should delete expense`() {
    val categoryId = createCategory(name = "Default Category").id
    val title = "Expense"
    val id = createExpense(title = title, categoryId = categoryId).id

    webTestClient.delete().uri("/api/v1/expense/$id").exchange().expectStatus().isNoContent
  }
}
