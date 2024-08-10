package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BudgetRepository : ReactiveCrudRepository<BudgetEntity, Long>
