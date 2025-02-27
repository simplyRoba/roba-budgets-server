package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.rest.model.Income
import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import de.simplyroba.suite.budgets.rest.model.IncomeUpdate
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.expectBody

class IncomeIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return income list`() {
    val size = 3

    (1..size).forEach { i -> createIncome(title = "Income $i") }

    webTestClient
      .get()
      .uri("/api/v1/income")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(size)
  }

  @Test
  fun `should return income list for year and month`() {
    val year = 2021
    val month = 10
    val startDate = LocalDate.parse("$year-$month-01")
    val endDate = startDate.plusDays(30)
    val outsideDate = startDate.plusMonths(1)

    createIncome(title = "Income start", dueDate = startDate)
    createIncome(title = "Income end", dueDate = endDate)
    createIncome(title = "Income outside", dueDate = outsideDate)

    webTestClient
      .get()
      .uri { builder ->
        builder.path("/api/v1/income/year/{year}/month/{month}").build(year, month)
      }
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(2)
  }

  @Test
  fun `should return income by id`() {
    val title = "Income"
    val id = createIncome(title).id

    webTestClient
      .get()
      .uri("/api/v1/income/$id")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Income>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(title)
      }
  }

  @Test
  fun `should return 404 when income not found on get`() {
    val id = 1

    webTestClient.get().uri("/api/v1/income/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create income`() {
    val title = "Income"
    val amountInCents = 1000
    val dueDate = LocalDate.now()

    webTestClient
      .post()
      .uri("/api/v1/income")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(IncomeCreate(title, amountInCents, dueDate))
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<Income>()
      .consumeWith {
        assertThat(it.responseBody?.id).isNotNull
        assertThat(it.responseBody?.title).isEqualTo(title)
        assertThat(it.responseBody?.amountInCents).isEqualTo(amountInCents)
        assertThat(it.responseBody?.dueDate).isEqualTo(dueDate)
      }
  }

  @Test
  fun `should update income`() {
    val title = "Income"
    val id = createIncome(title).id
    val updatedTitle = "Updated Income"
    val updatedAmountInCents = 1000
    val updatedDueDate = LocalDate.now()

    webTestClient
      .put()
      .uri("/api/v1/income/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(IncomeUpdate(updatedTitle, updatedAmountInCents, updatedDueDate))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Income>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(updatedTitle)
        assertThat(it.responseBody?.amountInCents).isEqualTo(updatedAmountInCents)
        assertThat(it.responseBody?.dueDate).isEqualTo(updatedDueDate)
      }
  }

  @Test
  fun `should return 404 when income not found on update`() {
    val id = 1

    webTestClient
      .put()
      .uri("/api/v1/income/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(IncomeUpdate("Updated Income", 1000, LocalDate.now()))
      .exchange()
      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should delete income`() {
    val id = createIncome().id

    webTestClient.delete().uri("/api/v1/income/$id").exchange().expectStatus().isNoContent
  }
}
