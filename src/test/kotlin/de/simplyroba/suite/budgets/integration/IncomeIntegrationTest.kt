package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.rest.model.Income
import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import de.simplyroba.suite.budgets.rest.model.IncomeUpdate
import java.time.OffsetDateTime
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
  fun `should return income list between dates`() {
    val startDate = OffsetDateTime.now()
    val endDate = startDate.plusDays(1)
    val outsideDate = endDate.plusDays(1)

    createIncome(title = "Income start", dueDate = startDate)
    createIncome(title = "Income end", dueDate = endDate)
    createIncome(title = "Income outside", dueDate = outsideDate)

    webTestClient
      .get()
      .uri { builder ->
        builder
          .path("/api/v1/income")
          .queryParam("startDate", "{1}")
          .queryParam("endDate", "{2}")
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
    val id = 1L

    webTestClient.get().uri("/api/v1/income/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create income`() {
    val title = "Income"
    val amountInCents = 1000
    val dueDate = OffsetDateTime.now()

    webTestClient
      .post()
      .uri("/api/v1/income")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(IncomeCreate(title = title, amountInCents = amountInCents, dueDate = dueDate))
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<Income>()
      .consumeWith {
        assertThat(it.responseBody?.id).isNotNull
        assertThat(it.responseBody?.title).isEqualTo(title)
        assertThat(it.responseBody?.amountInCents).isEqualTo(amountInCents)
        assertThat(it.responseBody?.dueDate).isAtSameInstantAs(dueDate)
      }
  }

  @Test
  fun `should update income`() {
    val title = "Income"
    val id = createIncome(title).id
    val updatedTitle = "Updated Income"

    webTestClient
      .put()
      .uri("/api/v1/income/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(
        IncomeUpdate(title = updatedTitle, amountInCents = 1000, dueDate = OffsetDateTime.now())
      )
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Income>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(updatedTitle)
      }
  }

  @Test
  fun `should return 404 when income not found on update`() {
    val id = 1L

    webTestClient
      .put()
      .uri("/api/v1/income/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(
        IncomeUpdate(title = "Updated Income", amountInCents = 1000, dueDate = OffsetDateTime.now())
      )
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
