package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class CategoryCreateToEntityConverterTest {

  private val converter = CategoryCreateToEntityConverter()

  @Test
  fun `should convert create to entity`() {
    val categoryCreate =
      CategoryCreate(name = "Test Category", disabled = false, parentCategoryId = 1)

    val result = converter.convert(categoryCreate)

    assertThat(result.name).isEqualTo(categoryCreate.name)
    assertThat(result.disabled).isEqualTo(categoryCreate.disabled)
    assertThat(result.parentCategoryId).isEqualTo(categoryCreate.parentCategoryId)
  }
}
