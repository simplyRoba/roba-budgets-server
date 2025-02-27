package de.simplyroba.suite.budgets.rest.model

data class CategoryTree(
	val id: Long,
	val name: String,
	val disabled: Boolean,
	val subCategory: List<CategoryTree>,
)

data class Category(
	val id: Long,
	val name: String,
	val disabled: Boolean,
	val parentCategoryId: Long?,
)

data class CategoryCreate(val name: String, val disabled: Boolean, val parentCategoryId: Long?)

typealias CategoryUpdate = CategoryCreate
