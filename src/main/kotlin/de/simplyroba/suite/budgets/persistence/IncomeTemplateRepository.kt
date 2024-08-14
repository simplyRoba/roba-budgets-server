package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface IncomeTemplateRepository : ReactiveCrudRepository<IncomeTemplateEntity, Long>
