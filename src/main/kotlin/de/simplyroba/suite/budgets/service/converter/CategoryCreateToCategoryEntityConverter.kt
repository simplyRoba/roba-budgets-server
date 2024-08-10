package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import org.springframework.stereotype.Component

@Component
class CategoryCreateToCategoryEntityConverter : Converter<CategoryCreate, CategoryEntity> {
  override fun convert(source: CategoryCreate): CategoryEntity {
    return CategoryEntity(name = source.name, parentCategoryId = source.parentCategoryId)
  }
}
