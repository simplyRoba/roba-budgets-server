package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.rest.model.Category
import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import de.simplyroba.suite.budgets.rest.model.CategoryTree
import de.simplyroba.suite.budgets.rest.model.CategoryUpdate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.expectBody

class CategoryIntegrationTest : AbstractIntegrationTest() {

  @Test
  fun `should return category list`() {
    val size = 3

    (1..size).forEach { i -> createCategory(name = "Category $i") }

    webTestClient
      .get()
      .uri("/api/v1/category")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.length()")
      .isEqualTo(size)
  }

  @Test
  fun `should return category tree`() {
    val id1 = createCategory("Root-Category 1").id
    val id2 = createCategory("Root-Category 2").id

    createCategory("Sub-Category 1-1", id1)
    createCategory("Sub-Category 2-1", id2)
    createCategory("Sub-Category 2-2", id2)

    webTestClient
      .get()
      .uri("/api/v1/category/tree")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<List<CategoryTree>>()
      .consumeWith { response ->
        val categoryTree = response.responseBody!!
        assertThat(categoryTree).hasSize(2)
        assertThat(categoryTree.first { it.id == id1 }.subCategory).hasSize(1)
        assertThat(categoryTree.first { it.id == id2 }.subCategory).hasSize(2)
      }
  }

  @Test
  fun `should return category by id`() {
    val name = "Category"
    val id = createCategory(name).id

    webTestClient
      .get()
      .uri("/api/v1/category/$id")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Category>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.name).isEqualTo(name)
      }
  }

  @Test
  fun `should return 404 when category not found on get`() {
    val id = 1

    webTestClient.get().uri("/api/v1/category/$id").exchange().expectStatus().isNotFound
  }

  @Test
  fun `should create category`() {
    val name = "New Category"

    webTestClient
      .post()
      .uri("/api/v1/category")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(CategoryCreate(name, null))
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody<Category>()
      .consumeWith {
        assertThat(it.responseBody?.id).isNotNull
        assertThat(it.responseBody?.name).isEqualTo(name)
      }
  }

  @Test
  fun `should update category`() {
    val name = "Category"
    val id = createCategory(name).id
    val updatedName = "Updated Category"

    webTestClient
      .put()
      .uri("/api/v1/category/{id}", id)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(CategoryUpdate("Updated Category", null))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Category>()
      .consumeWith {
        assertThat(it.responseBody?.id).isEqualTo(id)
        assertThat(it.responseBody?.name).isEqualTo(updatedName)
      }
  }

  @Test
  fun `should return 404 when category not found on update`() {
    val id = 1

    webTestClient
      .put()
      .uri("/api/v1/category/$id")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(CategoryUpdate("Category", null))
      .exchange()
      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should delete category`() {
    val id = createCategory().id

    webTestClient.delete().uri("/api/v1/category/$id").exchange().expectStatus().isNoContent
  }
}
