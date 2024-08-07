package de.simplyroba.suite.budgets.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface IncomeRepository : ReactiveCrudRepository<Income, Long> {}
