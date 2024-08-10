package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.Category
import de.simplyroba.suite.budgets.rest.model.CategoryCreate
import de.simplyroba.suite.budgets.rest.model.CategoryTree
import de.simplyroba.suite.budgets.rest.model.CategoryUpdate
import de.simplyroba.suite.budgets.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/category")
class CategoryController(
  private val categoryService: CategoryService,
) {

  @GetMapping
  fun getCategoryList(): Flux<Category> {
    return categoryService.findAll()
  }

  @GetMapping("/tree")
  fun getCategoryTree(): Flux<CategoryTree> {
    return categoryService.buildCategoryTree()
  }

  @GetMapping("/{id}")
  fun getCategoryById(
    @PathVariable id: Long,
  ): Mono<Category> {
    return categoryService.findById(id)
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createCategory(
    @RequestBody category: CategoryCreate,
  ): Mono<Category> {
    return categoryService.createCategory(category)
  }

  @PutMapping("/{id}")
  fun updateCategory(
    @PathVariable id: Long,
    @RequestBody category: CategoryUpdate,
  ): Mono<Category> {
    return categoryService.updateCategory(id, category)
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteCategory(
    @PathVariable id: Long,
  ): Mono<Void> {
    return categoryService.deleteCategory(id)
  }
}
