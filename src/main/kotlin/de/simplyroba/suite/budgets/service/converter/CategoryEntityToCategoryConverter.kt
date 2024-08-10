package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.rest.model.Category
import org.springframework.stereotype.Component

@Component
class CategoryEntityToCategoryConverter : Converter<CategoryEntity, Category> {
  override fun convert(source: CategoryEntity): Category {
    return Category(id = source.id, name = source.name, parentCategoryId = source.parentCategoryId)
  }
}
