package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CategoryEntityToCategoryConverterTest {

  private val converter = CategoryEntityToCategoryConverter()

  @Test
  fun `should convert entity to dto`() {
    val entity = CategoryEntity(id = 1, name = "name", parentCategoryId = 2)

    val dto = converter.convert(entity)

    assertEquals(entity.id, dto.id)
    assertEquals(entity.name, dto.name)
    assertEquals(entity.parentCategoryId, dto.parentCategoryId)
  }
}
