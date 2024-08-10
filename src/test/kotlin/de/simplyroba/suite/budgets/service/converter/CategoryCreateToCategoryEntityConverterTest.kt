package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class CategoryCreateToCategoryEntityConverterTest {

  private val converter = CategoryCreateToCategoryEntityConverter()

  @Test
  fun `should convert create to entity`() {
    val categoryCreate = CategoryCreate(name = "Test Category", parentCategoryId = 1)

    val result = converter.convert(categoryCreate)

    assertThat(result.name).isEqualTo(categoryCreate.name)
    assertThat(result.parentCategoryId).isEqualTo(categoryCreate.parentCategoryId)
  }
}
