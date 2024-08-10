package de.simplyroba.suite.budgets.rest.model

data class CategoryTree(
  val id: Long,
  val name: String,
  val subCategory: List<CategoryTree>,
)

data class Category(
  val id: Long,
  val name: String,
  val parentCategoryId: Long?,
)

data class CategoryCreate(
  val name: String,
  val parentCategoryId: Long?,
)

typealias CategoryUpdate = CategoryCreate
