package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CategoryRepository : ReactiveCrudRepository<CategoryEntity, Long>
