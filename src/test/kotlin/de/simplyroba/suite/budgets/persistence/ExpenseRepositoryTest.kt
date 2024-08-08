package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractBaseServiceTest
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseType
import org.junit.jupiter.api.Disabled
import java.time.OffsetDateTime
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class ExpenseRepositoryTest : AbstractBaseServiceTest() {

  @Autowired private lateinit var expenseRepository: ExpenseRepository

  @Test
  @Disabled
  fun `should read expense entry`() {
    expenseRepository
      .save(
        ExpenseEntity(
          title = "title",
          amountInCents = 100,
          dueDate = OffsetDateTime.now(),
          type = ExpenseType.FIX,
          categoryId = 1,
          budgetId = 1
        )
      )
      .block()

    expenseRepository.findAll().log().test().expectNextCount(1).verifyComplete()
  }
}
