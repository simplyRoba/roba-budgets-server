package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.rest.model.Budget
import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.expectBody

class BudgetIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return budget list`() {
    val size = 3

    (1..size).forEach { i -> createBudget(name = "Budget $i") }

    webTestClient
      .get()
      .uri("/api/v1/budget")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(size)
  }

  @Test
  fun `should return budget by id`() {
    val name = "Budget"
    val id = createBudget(name).id

    webTestClient
      .get()
      .uri("/api/v1/budget/$id")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Budget>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.name).isEqualTo(name)
      }
  }

  @Test
  fun `should return 404 when budget not found on get`() {
    val id = 1

    webTestClient.get().uri("/api/v1/budget/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create budget`() {
    val name = "Budget"
    val savingAmountInCents = 1000

    webTestClient
      .post()
      .uri("/api/v1/budget")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(BudgetCreate(name, savingAmountInCents))
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<Budget>()
      .consumeWith {
        assertThat(it.responseBody?.id).isNotNull
        assertThat(it.responseBody?.name).isEqualTo(name)
        assertThat(it.responseBody?.savingAmountInCents).isEqualTo(savingAmountInCents)
      }
  }

  @Test
  fun `should update budget`() {
    val name = "Budget"
    val id = createBudget(name).id
    val updatedName = "Updated Budget"
    val updatedSavingAmountInCents = 2000

    webTestClient
      .put()
      .uri("/api/v1/budget/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(BudgetCreate(updatedName, updatedSavingAmountInCents))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Budget>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.name).isEqualTo(updatedName)
        assertThat(it.responseBody?.savingAmountInCents).isEqualTo(updatedSavingAmountInCents)
      }
  }

  @Test
  fun `should return 404 when budget not found on update`() {
    val id = 1

    webTestClient
      .put()
      .uri("/api/v1/budget/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(BudgetCreate("Name", 99))
      .exchange()
      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should delete budget`() {
    val id = createBudget().id

    webTestClient.delete().uri("/api/v1/budget/$id").exchange().expectStatus().isNoContent
  }
}
