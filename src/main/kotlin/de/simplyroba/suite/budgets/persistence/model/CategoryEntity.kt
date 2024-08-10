package de.simplyroba.suite.budgets.persistence.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("category")
data class CategoryEntity(
  @Id val id: Long = 0,
  var name: String,
  var parentCategoryId: Long?,
)
