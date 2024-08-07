package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractBaseServiceTest
import java.time.OffsetDateTime
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class IncomeRepositoryTest : AbstractBaseServiceTest() {

  @Autowired private lateinit var incomeRepository: IncomeRepository

  @Test
  fun `should read income entry`() {
    incomeRepository.save(Income(amountInCents = 100, dueDate = OffsetDateTime.now())).block()

    incomeRepository.findAll().test().expectNextCount(1).verifyComplete()
  }
}
