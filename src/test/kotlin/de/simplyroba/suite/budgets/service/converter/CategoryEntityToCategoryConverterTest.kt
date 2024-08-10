package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CategoryEntityToCategoryConverterTest {

  private val converter = CategoryEntityToCategoryConverter()

  @Test
  fun `should convert entity to dto`() {
    val entity = CategoryEntity(id = 1, name = "name", parentCategoryId = 2)

    val result = converter.convert(entity)

    assertThat(result.id).isEqualTo(entity.id)
    assertThat(result.name).isEqualTo(entity.name)
    assertThat(result.parentCategoryId).isEqualTo(entity.parentCategoryId)
  }
}
