package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import org.springframework.stereotype.Component

@Component
class CategoryCreateToEntityConverter : Converter<CategoryCreate, CategoryEntity> {
  override fun convert(source: CategoryCreate): CategoryEntity {
    return CategoryEntity(
      name = source.name,
      disabled = source.disabled,
      parentCategoryId = source.parentCategoryId,
    )
  }
}
