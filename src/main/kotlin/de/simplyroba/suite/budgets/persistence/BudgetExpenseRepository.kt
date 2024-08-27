package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.BudgetExpenseEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BudgetExpenseRepository : ReactiveCrudRepository<BudgetExpenseEntity, Long>
