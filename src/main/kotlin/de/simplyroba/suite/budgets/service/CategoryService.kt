package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.CategoryRepository
import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.Category
import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import de.simplyroba.suite.budgets.rest.model.CategoryTree
import de.simplyroba.suite.budgets.rest.model.CategoryUpdate
import de.simplyroba.suite.budgets.service.converter.Converter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryService(
  private val categoryRepository: CategoryRepository,
  private val categoryEntityToCategoryConverter: Converter<CategoryEntity, Category>,
  private val categoryCreateToEntityConverter: Converter<CategoryCreate, CategoryEntity>
) {

  fun findAll(): Flux<Category> {
    return categoryRepository.findAll().map(categoryEntityToCategoryConverter::convert)
  }

  fun buildCategoryTree(): Flux<CategoryTree> {
    return categoryRepository.findAll().collectList().flatMapMany { categories ->
      val categoryMap = categories.associateBy { it.id }
      val topLevelCategories = categories.filter { it.parentCategoryId == null }
      Flux.fromIterable(topLevelCategories.map { buildTreeRecursively(it, categoryMap) })
    }
  }

  private fun buildTreeRecursively(
    category: CategoryEntity,
    categoryMap: Map<Long, CategoryEntity>
  ): CategoryTree {
    val subCategory = categoryMap.values.filter { it.parentCategoryId == category.id }
    return CategoryTree(
      id = category.id,
      name = category.name,
      disabled = category.disabled,
      subCategory = subCategory.map { buildTreeRecursively(it, categoryMap) }
    )
  }

  fun findById(id: Long): Mono<Category> {
    return categoryRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Category with id $id not found")))
      .map(categoryEntityToCategoryConverter::convert)
  }

  @Transactional
  fun createCategory(category: CategoryCreate): Mono<Category> {
    return categoryRepository
      .save(categoryCreateToEntityConverter.convert(category))
      .map(categoryEntityToCategoryConverter::convert)
  }

  @Transactional
  fun updateCategory(id: Long, categoryUpdate: CategoryUpdate): Mono<Category> {
    return categoryRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Category with id $id not found")))
      .map { existingCategory ->
        existingCategory.apply {
          name = categoryUpdate.name
          disabled = categoryUpdate.disabled
          parentCategoryId = categoryUpdate.parentCategoryId
        }
      }
      .flatMap(categoryRepository::save)
      .map(categoryEntityToCategoryConverter::convert)
  }

  @Transactional
  fun deleteCategory(id: Long): Mono<Void> {
    return categoryRepository.deleteById(id)
  }
}
