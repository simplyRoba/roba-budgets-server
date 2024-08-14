package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import java.time.OffsetDateTime
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class ExpenseRepositoryTest : AbstractIntegrationTest() {

  @Autowired private lateinit var expenseRepository: ExpenseRepository

  @Test
  fun `should read expense entry`() {
    expenseRepository
      .save(
        ExpenseEntity(
          title = "title",
          amountInCents = 100,
          dueDate = OffsetDateTime.now(),
          type = ExpenseTypePersistenceEnum.FIX,
          categoryId = 1,
          budgetId = 1
        )
      )
      .block()

    expenseRepository.findAll().log().test().expectNextCount(1).verifyComplete()
  }
}
