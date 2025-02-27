package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.rest.model.IncomeTemplate
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateCreate
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateUpdate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.expectBody

class IncomeTemplateIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return income template list`() {
    val size = 3

    (1..size).map { createIncomeTemplate(title = "Income Template $it") }

    webTestClient
      .get()
      .uri("/api/v1/income-template")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(size)
  }

  @Test
  fun `should return income template by id`() {
    val title = "Income Template"
    val id = createIncomeTemplate(title = title).id

    webTestClient
      .get()
      .uri("/api/v1/income-template/$id")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<IncomeTemplate>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(title)
      }
  }

  @Test
  fun `should return 404 when income template not found on get`() {
    val id = 1

    webTestClient.get().uri("/api/v1/income-template/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create income template`() {
    val title = "Income Template"
    val amountInCents = 999

    webTestClient
      .post()
      .uri("/api/v1/income-template")
      .bodyValue(
        IncomeTemplateCreate(
          title = title,
          amountInCents = amountInCents,
          repeatInterval = RepeatInterval.MONTHLY,
        )
      )
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<IncomeTemplate>()
      .consumeWith {
        assertThat(it.responseBody?.id).isNotNull
        assertThat(it.responseBody?.title).isEqualTo(title)
        assertThat(it.responseBody?.amountInCents).isEqualTo(amountInCents)
      }
  }

  @Test
  fun `should update income template`() {
    val title = "Income Template"
    val amountInCents = 999
    val updatedTitle = "Updated Income Template"
    val updatedAmountInCents = 1000

    val id = createIncomeTemplate(title = title, amountInCents = amountInCents).id

    webTestClient
      .put()
      .uri("/api/v1/income-template/$id")
      .bodyValue(
        IncomeTemplateUpdate(
          title = updatedTitle,
          amountInCents = updatedAmountInCents,
          repeatInterval = RepeatInterval.MONTHLY,
        )
      )
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<IncomeTemplate>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(updatedTitle)
        assertThat(it.responseBody?.amountInCents).isEqualTo(updatedAmountInCents)
      }
  }

  @Test
  fun `should return 404 when income template not found on update`() {
    val id = 1

    webTestClient
      .put()
      .uri("/api/v1/income-template/$id")
      .bodyValue(
        IncomeTemplateUpdate(
          title = "Updated Income Template",
          amountInCents = 1000,
          repeatInterval = RepeatInterval.MONTHLY,
        )
      )
      .exchange()
      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should delete income template`() {
    val id = createIncomeTemplate().id

    webTestClient.delete().uri("/api/v1/income-template/$id").exchange().expectStatus().isNoContent
  }
}
