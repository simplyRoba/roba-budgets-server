package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplate
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateCreate
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateUpdate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.expectBody

class FixExpenseTemplateIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return fix expense template list`() {
    val categoryId = createCategory(name = "Default category").id
    val size = 3

    (1..size).map {
      createFixExpenseTemplate(title = "Fix Expense Template $it", categoryId = categoryId)
    }

    webTestClient
      .get()
      .uri("/api/v1/fix-expense-template")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(size)
  }

  @Test
  fun `should return fix expense template by id`() {
    val categoryId = createCategory(name = "Default category").id
    val title = "Fix Expense Template"
    val id = createFixExpenseTemplate(title = title, categoryId = categoryId).id

    webTestClient
      .get()
      .uri("/api/v1/fix-expense-template/$id")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<FixExpenseTemplate>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(title)
      }
  }

  @Test
  fun `should return 404 when fix expense template not found on get`() {
    val id = 1

    webTestClient.get().uri("/api/v1/fix-expense-template/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create fix expense template`() {
    val categoryId = createCategory(name = "Default category").id
    val title = "Fix Expense Template"
    val amountInCents = 999
    val repeatInterval = RepeatInterval.MONTHLY

    webTestClient
      .post()
      .uri("/api/v1/fix-expense-template")
      .bodyValue(
        FixExpenseTemplateCreate(
          title = title,
          amountInCents = amountInCents,
          repeatInterval = repeatInterval,
          categoryId = categoryId,
        )
      )
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<FixExpenseTemplate>()
      .consumeWith {
        assertThat(it.responseBody?.id).isNotNull
        assertThat(it.responseBody?.title).isEqualTo(title)
        assertThat(it.responseBody?.amountInCents).isEqualTo(amountInCents)
        assertThat(it.responseBody?.categoryId).isEqualTo(categoryId)
      }
  }

  @Test
  fun `should update fix expense template`() {
    val categoryId = createCategory(name = "Default category").id
    val id =
      createFixExpenseTemplate(
          title = "Fix Expense Template",
          amountInCents = 999,
          repeatInterval = RepeatIntervalPersistenceEnum.MONTHLY,
          categoryId = categoryId,
        )
        .id

    val updatedTitle = "Updated Fix Expense Template"
    val updatedAmountInCents = 123
    val updatedRepeatInterval = RepeatInterval.QUARTERLY

    webTestClient
      .put()
      .uri("/api/v1/fix-expense-template/$id")
      .bodyValue(
        FixExpenseTemplateUpdate(
          title = updatedTitle,
          amountInCents = updatedAmountInCents,
          repeatInterval = updatedRepeatInterval,
          categoryId = categoryId,
        )
      )
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<FixExpenseTemplate>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.title).isEqualTo(updatedTitle)
        assertThat(it.responseBody?.amountInCents).isEqualTo(updatedAmountInCents)
        assertThat(it.responseBody?.categoryId).isEqualTo(categoryId)
      }
  }

  @Test
  fun `should return 404 when fix expense template not found on update`() {
    val id = 1

    webTestClient
      .put()
      .uri("/api/v1/fix-expense-template/$id")
      .bodyValue(
        FixExpenseTemplateUpdate(
          title = "Updated Fix Expense Template",
          amountInCents = 1000,
          repeatInterval = RepeatInterval.MONTHLY,
          categoryId = 1,
        )
      )
      .exchange()
      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should delete fix expense template`() {
    val categoryId = createCategory(name = "Default category").id
    val id = createFixExpenseTemplate(categoryId = categoryId).id

    webTestClient
      .delete()
      .uri("/api/v1/fix-expense-template/$id")
      .exchange()
      .expectStatus()
      .isNoContent
  }
}
