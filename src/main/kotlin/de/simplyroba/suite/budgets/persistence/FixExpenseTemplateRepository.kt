package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.FixExpenseTemplateEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface FixExpenseTemplateRepository : ReactiveCrudRepository<FixExpenseTemplateEntity, Long>
