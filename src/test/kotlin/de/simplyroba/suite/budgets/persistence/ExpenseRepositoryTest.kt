package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractBaseServiceTest
import java.time.OffsetDateTime
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class ExpenseRepositoryTest : AbstractBaseServiceTest() {

  @Autowired private lateinit var expenseRepository: ExpenseRepository

  @Test
  fun `should read expense entry`() {
    expenseRepository
      .save(Expense(amountInCents = 100, dueDate = OffsetDateTime.now(), type = ExpenseType.FIX))
      .block()

    expenseRepository.findAll().log().test().expectNextCount(1).verifyComplete()
  }
}
