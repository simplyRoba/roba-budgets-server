package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ExpenseRepository : ReactiveCrudRepository<ExpenseEntity, Long> {}
